package server.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import server.data.EquipmentData;
import server.service.*;

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

    @Autowired
    private EquipmentOnlineMonitor onlineMonitor;

    @MessageMapping("/equipments")
    public void save(EquipmentData equipment) {
        equipmentService.save(equipment);
        snapshotService.record(equipment);
        onlineMonitor.record(equipment);
        alertService.validate(equipment);
        pushService.send(equipment);
    }
}