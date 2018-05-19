package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.data.EquipmentData;
import server.service.EquipmentService;
import server.service.PushService;

import java.util.Collection;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private PushService pushService;

    @Autowired
    private EquipmentService equipmentService;

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
        pushService.sendEquipment(equipment);
    }

}