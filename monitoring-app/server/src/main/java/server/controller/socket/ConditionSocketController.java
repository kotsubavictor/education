package server.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import server.data.ConditionData;
import server.service.ConditionDataService;
import server.service.PushService;

@Controller
public class ConditionSocketController {

    @Autowired
    private ConditionDataService releConditionDataService;

    @Autowired
    private PushService pushService;

    @MessageMapping("/conditions")
    public void save(ConditionData data) {
        releConditionDataService.save(data);
        pushService.send(data);
    }
}
