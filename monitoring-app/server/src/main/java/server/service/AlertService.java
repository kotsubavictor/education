package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.domain.Equipment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = "singleton")
public class AlertService {
    private static final String ALERT_MESSAGE = "ALERT - TEMPERATURE";

    @Value("${server.mail.to}")
    private String mailTo;

    @Value("${server.alert.high.temp}")
    private int alertHightTemp;

    @Value("${server.alert.low.temp}")
    private int alertLowtTemp;

    private Map<String, String> alerts = new ConcurrentHashMap<>();

    @Autowired
    private JavaMailSender emailSender;

    public void validate(EquipmentData data) {
        validate(data.getName(), data.getTemperature());
    }

    private void validate(Equipment equipment) {
        validate(equipment.getName(), equipment.getTemperature());
    }

    private void validate(String name, Float temperature) {
        if ((temperature > alertHightTemp || temperature < alertLowtTemp) && !alerts.containsKey(name)) {
            alerts.put(name, name);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailTo);
            message.setSubject(ALERT_MESSAGE);
            message.setText(name.toUpperCase() + ": " + temperature);
            emailSender.send(message);
        }
    }
}
