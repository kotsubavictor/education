package server.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import server.WebAppConst;
import server.data.Equipment;
import server.repository.EquipmentRepository;

@Controller
public class EquipmentPushController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @MessageMapping("/equipments")
    @SendTo(WebAppConst.WEBSOCKET_TOPIC_EQUIPMENT)
    public Equipment save(Equipment equipment) {
        equipmentRepository.save(new server.domain.Equipment(equipment.getName(), equipment.getTemperature()));
        return equipment;
    }
}