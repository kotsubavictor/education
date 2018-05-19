package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import server.data.Equipment;
import server.domain.TemperatureSnapshot;

import java.util.Collection;

@Component
public class PushService {

    public static final String WEBSOCKET_TOPIC_EQUIPMENT = "/topic/equipments";

    @Autowired
    private SimpMessagingTemplate webSocket;

    public void sendEquipment(Equipment equipment) {
        webSocket.convertAndSend(WEBSOCKET_TOPIC_EQUIPMENT, equipment);
    }

    public void sendTemperatureSnapshot(Collection<TemperatureSnapshot> temperatures) {
//        todo: send
    }
}
