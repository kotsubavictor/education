package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.data.ReleData;
import server.service.ReleDataService;

import java.util.Collection;

@RestController
@RequestMapping("/reles")
public class ReleController {

    @Autowired
    @Qualifier("mqtt_outbound_channel")
    private MessageChannel messageChannel;

    @Autowired
    private ReleDataService releDataService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<ReleData> list() {
        return releDataService.list();
    }

    @RequestMapping(value = "/{name}/{state}", method = RequestMethod.GET, produces = "application/json")
    public void test(@PathVariable String name, @PathVariable String state) {
//        todo: remove later
        messageChannel.send(MessageBuilder.withPayload(state)
                .setHeader(MqttHeaders.TOPIC, "cmnd/sonoff/" + name + "/POWER")
                .build());
    }

}
