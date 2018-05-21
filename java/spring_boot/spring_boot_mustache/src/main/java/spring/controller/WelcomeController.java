package spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Controller
public class WelcomeController {

    @Value("${app.welcome.message}")
    private String MESSAGE = "";

    @Value("${app.welcome.title}")
    private String TITLE = "";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("title", TITLE);
        model.put("message", MESSAGE);
        return "welcome";
    }

    @RequestMapping("/serviceUnavailable")
    public String serviceUnavailable() {
        throw new RuntimeException("ABC");
    }

    @RequestMapping("/bang")
    public String bang() {
        throw new RuntimeException("Boom");
    }

    @RequestMapping("/insufficientStorage")
    public String insufficientStorage() {
        throw new InsufficientStorageException();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    private static class ServiceUnavailableException extends RuntimeException {

    }

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    private static class InsufficientStorageException extends RuntimeException {

    }
}
