package com.sunway.controller;

import com.sunway.model.IoDriver;
import com.sunway.service.impl.IoDriverServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class IoDriverController {

    @Autowired
    private IoDriverServiceImpl ioDriverService;

    @RequestMapping(value="/drivers")
    public void addIoDrivers(String uaServer, List<IoDriver> drivers){
        ioDriverService.addIoDrivers(uaServer, drivers);
    }

    @RequestMapping(value="/drivers-test")
    public String addIoDriversTest(){
        List<IoDriver> drivers = new ArrayList();
        drivers.add(new IoDriver("IEC104TCP"));
        drivers.add(new IoDriver("ModbusUdpServer"));
        addIoDrivers("ioserver", drivers);
        return "menu";
    }
}
