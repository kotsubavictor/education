package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import server.data.*;

@Component
public class PushService {

    public static final String WEBSOCKET_TOPIC_RELE = "/topic/reles";
    public static final String WEBSOCKET_TOPIC_EQUIPMENT = "/topic/equipments";
    public static final String WEBSOCKET_TOPIC_TEMPERATURES = "/topic/temperatures";
    public static final String WEBSOCKET_TOPIC_CONDITIONS = "/topic/conditions";
    public static final String WEBSOCKET_TOPIC_ALERTS = "/topic/alerts";

    @Autowired
    private SimpMessagingTemplate webSocket;

    public void send(EquipmentData equipment) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_EQUIPMENT, equipment);
    }

    public void send(TemperatureSnapshotData temperatures) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_TEMPERATURES, temperatures);
    }

    public void send(ReleData rele) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_RELE, rele);
    }

    public void send(ConditionData condition) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_CONDITIONS, condition);
    }

    public void send(AlertData alert) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_ALERTS, alert);
    }
}
