package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.WebAppConst;
import server.data.Equipment;
import server.data.MockUtils;

import java.util.Collection;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<Equipment> list() {
        return MockUtils.getEquipment();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public Equipment list(@PathVariable String name) {
        return MockUtils.getEquipment(name);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Equipment tmp) {
        Equipment equipment = MockUtils.getEquipment(tmp.getName());
        if (equipment == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        equipment.setTemperature(tmp.getTemperature());
        webSocket.convertAndSend(WebAppConst.WEBSOCKET_TOPIC_EQUIPMENT, equipment);

        return new ResponseEntity(HttpStatus.OK);
    }
}