package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.data.TemperatureSnapshotData;

@Component
public class PushService {

    public static final String WEBSOCKET_TOPIC_EQUIPMENT = "/topic/equipments";
    public static final String WEBSOCKET_TOPIC_TEMPERATURES = "/topic/temperatures";

    @Autowired
    private SimpMessagingTemplate webSocket;

    public void sendEquipment(EquipmentData equipment) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_EQUIPMENT, equipment);
    }

    public void sendTemperatureSnapshot(TemperatureSnapshotData temperatures) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_TEMPERATURES, temperatures);
    }
}
