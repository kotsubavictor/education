package client.scheduler;

import client.data.Equipment;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Component
public class EquipmentMonitor {

    @Value("${rest.equipment.endpoint}")
    private String endpoint;

    @Value("${equipment.name}")
    private String name;

    @Value("${bash.script}")
    private String script;

    private CommandLine commandline;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void setUp() throws URISyntaxException {
        commandline = new CommandLine(new File(script));
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        try {
            Equipment equipment = getEquipmentInfo();
            restTemplate.put(endpoint, equipment, equipment.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Equipment getEquipmentInfo() throws IOException, NumberFormatException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandline);
        Float temperature = Arrays.asList(outputStream.toString().split("\n"))
                .stream().map(Float::parseFloat).max(Float::compare).get();
        return new Equipment(name, temperature);
    }

}
