package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.AlertData;
import server.data.EquipmentData;
import server.domain.Alert;
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
    private static final String ALERT_MESSAGE = "ALERT - EQUIPMENT";

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    @Value("${server.mail.to}")
    private String mailTo;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private BashScriptService bashScriptService;

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
                Boolean shutdown = Boolean.FALSE;

                try {
                    engine.eval(alert.getCondition(), simpleBindings);
                    shutdown= (Boolean)((Bindings) simpleBindings.get("nashorn.global")).get("shutdown");
                } catch (ScriptException e) {
                }

                sendMessage(equipment, messages);
                if (shutdown) {
                    bashScriptService.shutdown(equipment.getName());
                }
            }
        });
    }

    public void sendMessage(String data) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setSubject(ALERT_MESSAGE);
        message.setText(data);
        emailSender.send(message);
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
            sendMessage(notification.toString());
        }
    }
}
