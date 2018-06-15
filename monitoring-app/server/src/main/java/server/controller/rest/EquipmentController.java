package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.data.EquipmentData;
import server.service.AlertService;
import server.service.EquipmentDataService;
import server.service.PushService;
import server.service.SnapshotService;

import java.util.Collection;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private PushService pushService;

    @Autowired
    private EquipmentDataService equipmentService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private SnapshotService snapshotService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<EquipmentData> list() {
        return equipmentService.list();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public EquipmentData list(@PathVariable String name) {
        return equipmentService.list(name);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public void update(@RequestBody EquipmentData equipment) {
        equipmentService.save(equipment);
        snapshotService.record(equipment);
        alertService.validate(equipment);
        pushService.send(equipment);
    }

}