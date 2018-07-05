package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.data.AccountSnapshotData;
import server.data.AlertData;
import server.service.AccountSnapshotService;

import java.util.Collection;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountSnapshotService accountService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<AccountSnapshotData> list() {
        return accountService.list();
    }
}
