package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.AlertData;
import server.data.ConditionData;
import server.data.EquipmentData;
import server.data.ReleData;
import server.domain.Alert;
import server.domain.Equipment;
import server.repository.AlertRepository;

import javax.script.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = "singleton")
public class AlertService {
    private static final String ALERT_MESSAGE = "ALERT - TEMPERATURE";

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    @Value("${server.mail.to}")
    private String mailTo;

    @Autowired
    private AlertRepository alertRepository;

    private Map<String, Set<String>> alerts = new ConcurrentHashMap<>();

    @Autowired
    private JavaMailSender emailSender;

    public void save(AlertData alert) {
        alertRepository.save(new Alert(alert.getName(), alert.getCondition(), alert.getActive()));
    }

    public Collection<AlertData> list() {
        final Collection<AlertData> alerts = new LinkedList<>();
        alertRepository.findAll().forEach((alert -> alerts.add(AlertData.from(alert))));
        return alerts;
    }

    public void validate(final EquipmentData equipment) {
        alertRepository.findById(equipment.getName()).ifPresent(alert -> {
            if (alert.getActive()) {
                LinkedList<String> messages = new LinkedList<>();
                SimpleBindings simpleBindings = new SimpleBindings();
                simpleBindings.put("equipment", equipment);
                simpleBindings.put("result", messages);

                try {
                    engine.eval(alert.getCondition(), simpleBindings);
                } catch (ScriptException e) {
                }

                sendMessage(equipment, messages);
            }
        });
    }

    @Scheduled(initialDelay = 60000, fixedRate = 300000)
    private void clearMessage() {
        alerts.clear();
    }

    private void sendMessage(EquipmentData equipment, LinkedList<String> messages) {
        if (messages.isEmpty()) {
            return;
        }

        Set<String> oldMessages = alerts.get(equipment.getName());
        StringBuilder notification = new StringBuilder();
        if (oldMessages == null) {
            oldMessages = ConcurrentHashMap.newKeySet();
            alerts.put(equipment.getName(), oldMessages);
        }

        for (String message : messages) {
            if (oldMessages.contains(message)) {
                continue;
            }

            oldMessages.add(message);
            notification.append('\n');
            notification.append(message);
        }

        if (notification.length() > 0) {
            notification.append('\n');
            notification.append(equipment.toString());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailTo);
            message.setSubject(ALERT_MESSAGE);
            message.setText(notification.toString());
            emailSender.send(message);
        }
    }
}
