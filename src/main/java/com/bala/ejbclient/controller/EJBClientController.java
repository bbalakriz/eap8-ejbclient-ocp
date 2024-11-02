package com.bala.ejbclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bala.ejbclient.EJBClientService;

@RestController
public class EJBClientController {

    private final EJBClientService ejbClientService;

    public EJBClientController(EJBClientService ejbClientService) {
        this.ejbClientService = ejbClientService;
    }

    @GetMapping("/invokeEJB")
    public String invokeEJB() {
        return ejbClientService.invokeRemoteEJB();
    }

}
