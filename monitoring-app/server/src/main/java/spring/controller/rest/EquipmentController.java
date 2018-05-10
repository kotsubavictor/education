package spring.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.WebAppConst;
import spring.data.Equipment;
import spring.data.MockUtils;

import java.util.Collection;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @RequestMapping(produces = "application/json")
    public Collection<Equipment> list() {
        return MockUtils.getEquipment();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public Equipment list(@PathVariable String name) {
        return MockUtils.getEquipment(name);
    }

    @RequestMapping(value = "/{name}/temperature/{temperature}", method = RequestMethod.PUT)
    public void updateTemperature(@PathVariable String name, @PathVariable Long temperature) {
        Equipment equipment = MockUtils.getEquipment(name);
        equipment.setTemperature(temperature);
        webSocket.convertAndSend(WebAppConst.WEBSOCKET_TOPIC_EQUIPMENT, equipment);
    }
}