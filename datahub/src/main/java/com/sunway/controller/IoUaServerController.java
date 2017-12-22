package com.sunway.controller;

import com.sunway.service.IoUaServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="server")
public class IoUaServerController {

    @Autowired
    private IoUaServerService ioUaServerService;

    @RequestMapping(value="/add")
    public String addIoUaServer(String uaServer){
        ioUaServerService.addIoUaServer(uaServer);
        return "menu";
    }

    /*@RequestMapping(value="/add-test")
    public String addIoUaServerTest(){
        addIoUaServer("ioserver2");
        return "menu";
    }*/
}
