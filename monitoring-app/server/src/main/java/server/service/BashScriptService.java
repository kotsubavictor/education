package server.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class BashScriptService {

    private final Logger logger = LoggerFactory.getLogger(BashScriptService.class);

    @Value("${script.dir}")
    private String scriptDir;

    @Autowired
    private AlertService alertService;

    public void shutdown(String name) {
        try {
            CommandLine commandLine = new CommandLine(new File(scriptDir + File.separator + name + ".sh"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine);
        } catch (IOException e) {
            alertService.sendMessage("Could not shutdown machine: " + name);
            logger.error("Could not shutdown machine: " + name, e);
        }
    }
}
