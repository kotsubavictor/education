package server.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import server.data.AlertData;
import server.service.AlertService;
import server.service.PushService;

@Controller
public class AlertsSocketController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private PushService pushService;

    @MessageMapping("/alerts")
    public void save(AlertData data) {
        alertService.save(data);
        pushService.send(data);
    }
}
