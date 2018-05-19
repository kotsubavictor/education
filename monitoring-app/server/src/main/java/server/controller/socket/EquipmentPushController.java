package server.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import server.data.Equipment;
import server.service.EquipmentService;
import server.service.PushService;

@Controller
public class EquipmentPushController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private PushService pushService;

    @MessageMapping("/equipments")
    public void save(Equipment equipment) {
        equipmentService.save(equipment);
        pushService.sendEquipment(equipment);
    }
}