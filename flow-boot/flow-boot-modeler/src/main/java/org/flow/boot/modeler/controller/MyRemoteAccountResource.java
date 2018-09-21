package org.flow.boot.modeler.controller;

import java.util.ArrayList;

import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class MyRemoteAccountResource {

    /**
     * GET /rest/account -> get the current user.
     */
    @GetMapping(value = "/rest/account", produces = "application/json")
    public UserRepresentation getAccount() {
        UserRepresentation user = new UserRepresentation();
        user.setId("1");
        user.setFirstName("songyz");
        user.setFullName("宋印赠");
        user.setLastName("宋印赠");
        user.setPrivileges(new ArrayList<>());
        user.getPrivileges().add("ROLE_ADMIN");
        user.getPrivileges().add("ROLE_USER");
        return user;

    }
}
