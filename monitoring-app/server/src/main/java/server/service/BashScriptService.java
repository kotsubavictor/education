package server.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class BashScriptService {

    @Value("${script.dir}")
    private String scriptDir;

    public void shutdown(String name) {
        try {
            CommandLine commandLine = new CommandLine(new File(scriptDir + File.separator + name + ".sh"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine);
//        todo: log results
            System.out.println("-----------------\n shutdown machine:" + name);
            System.out.println(outputStream);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
