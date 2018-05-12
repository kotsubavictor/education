package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.repository.EquipmentRepository;
import server.WebAppConst;
import server.data.Equipment;

import java.util.Collection;
import java.util.LinkedList;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<Equipment> list() {
        final Collection<Equipment> result = new LinkedList<>();
        equipmentRepository.findAll().forEach((equipment -> result.add(Equipment.from(equipment))));
        return result;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public Equipment list(@PathVariable String name) {
        Equipment equipment = equipmentRepository.findById(name).map(Equipment::from).get();
        return equipment;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public void update(@RequestBody Equipment tmp) {
        equipmentRepository.save(new server.domain.Equipment(tmp.getName(), tmp.getTemperature()));
        webSocket.convertAndSend(WebAppConst.WEBSOCKET_TOPIC_EQUIPMENT, tmp);
    }

}