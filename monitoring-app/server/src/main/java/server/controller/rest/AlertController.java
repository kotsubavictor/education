package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.data.AlertData;
import server.data.ConditionData;
import server.service.AlertService;
import server.service.ConditionDataService;
import server.service.PushService;

import java.util.Collection;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private PushService pushService;

    @RequestMapping(name = "/{name}",method = RequestMethod.PUT, produces = "application/json")
    public void save(AlertData alert) {
        alertService.save(alert);
        pushService.send(alert);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<AlertData> list() {
        return alertService.list();
    }
}
