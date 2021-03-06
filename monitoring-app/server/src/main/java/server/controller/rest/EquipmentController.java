package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.data.EquipmentData;
import server.service.*;

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

    @Autowired
    private EquipmentOnlineMonitor onlineMonitor;

    @Autowired
    private BashScriptService bashScriptService;

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
        onlineMonitor.record(equipment);
        alertService.validate(equipment);
        pushService.send(equipment);
    }

    @RequestMapping(value = "/{name}/shutdown", method = RequestMethod.GET, produces = "application/json")
    public void shutdown(@PathVariable String name) {
        bashScriptService.shutdown(name);
    }

}