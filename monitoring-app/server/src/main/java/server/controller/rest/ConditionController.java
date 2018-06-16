package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.data.ConditionData;
import server.service.ConditionDataService;
import server.service.PushService;

import java.util.Collection;

@RestController
@RequestMapping("/conditions")
public class ConditionController {

    @Autowired
    private ConditionDataService releConditionDataService;

    @Autowired
    private PushService pushService;

    @RequestMapping(name = "/{name}",method = RequestMethod.PUT, produces = "application/json")
    public void save(ConditionData condition) {
        releConditionDataService.save(condition);
        pushService.send(condition);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<ConditionData> list() {
        return releConditionDataService.list();
    }
}
