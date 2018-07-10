package client.scheduler;

import client.data.EquipmentData;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EquipmentMonitor {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentMonitor.class);

    @Value("${rest.equipment.endpoint}")
    private String endpoint;

    @Value("${equipment.name}")
    private String name;

    @Value("${bash.script}")
    private String monitorScript;

    @Value("${shutdown.script}")
    private String shutdownScript;

    @Value("${server.url}")
    private String serverUrl;

    private CommandLine commandline;

    private LocalDateTime offTime;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void setUp() {
        commandline = new CommandLine(new File(monitorScript));
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        try {
            EquipmentData equipment = getEquipmentInfo();
            restTemplate.put(endpoint, equipment, equipment.getName());
        } catch (IOException e) {
            logger.warn("Could not send temperature to server.", e);
        }
    }

    private EquipmentData getEquipmentInfo() throws IOException, NumberFormatException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandline);
        Float temperature = Arrays.asList(outputStream.toString().split("\n"))
                .stream().map(Float::parseFloat).max(Float::compare).get();
        return new EquipmentData(name, true, temperature, null);
    }

    private void shutdown() {
        try {
            CommandLine commandLine = new CommandLine(new File(shutdownScript));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine);
        } catch (IOException e) {
            logger.error("Could not shutdown machine", e);
        }
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    private void checkAccess() {
        if (hasAccessToServer()) {
            offTime = null;
        } else {
            if (offTime == null) {
                offTime = LocalDateTime.now();
            }

            if (offTime.isBefore(LocalDateTime.now().minusMinutes(5))) {
                shutdown();
            }
        }
    }

    private boolean hasAccessToServer() {
        boolean isOnline = false;
        InetAddress[] addresses;
        try {
            addresses = InetAddress.getAllByName(serverUrl);
        } catch (UnknownHostException ex) {
            return isOnline;
        }

        for (InetAddress address : addresses) {
            try {
                isOnline = address.isReachable(2000);
            } catch (IOException e) {
                isOnline = false;
            }

            if (isOnline) {
                break;
            }
        }

        return isOnline;
    }
}
