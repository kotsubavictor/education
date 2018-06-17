package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.data.ReleData;
import server.service.ConditionDataService;
import server.service.ReleDataService;

import java.util.Collection;

@RestController
@RequestMapping("/reles")
public class ReleController {

    @Autowired
    private ReleDataService releDataService;

    @Autowired
    private ConditionDataService conditionDataService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<ReleData> list() {
        return releDataService.list();
    }

    @RequestMapping(value = "/{name}/{state}", method = RequestMethod.GET, produces = "application/json")
    public void test(@PathVariable String name, @PathVariable Boolean state) {
//        todo: remove later
        conditionDataService.power(name, state);
    }

}
