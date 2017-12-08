package com.sunway.controller.api;

import com.sunway.model.IoDriver;
import com.sunway.service.impl.IoDriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IoDriverApi {

    @Autowired
    private IoDriverServiceImpl ioDriverService;

    @RequestMapping(value="/api-get-drivers")
    public List<IoDriver> getDrivers(String uaServer){
        return ioDriverService.queryIoDrivers(uaServer);
    }
}
