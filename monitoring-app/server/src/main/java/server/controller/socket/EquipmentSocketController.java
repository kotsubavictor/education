package server.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import server.data.EquipmentData;
import server.service.AlertService;
import server.service.EquipmentDataService;
import server.service.PushService;
import server.service.SnapshotService;

@Controller
public class EquipmentSocketController {

    @Autowired
    private EquipmentDataService equipmentService;

    @Autowired
    private PushService pushService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private SnapshotService snapshotService;

    @MessageMapping("/equipments")
    public void save(EquipmentData equipment) {
        equipmentService.save(equipment);
        snapshotService.record(equipment);
        alertService.validate(equipment);
        pushService.sendEquipment(equipment);
    }
}