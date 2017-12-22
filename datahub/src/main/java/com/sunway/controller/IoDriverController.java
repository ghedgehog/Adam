package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.service.IoDriverService;
import com.sunway.service.RealDataService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="driver")
public class IoDriverController {

    @Autowired
    private IoDriverService driverService;

    @Autowired
    private RealDataService dataService;

    @RequestMapping(value="/add")
    public void addIoDrivers(String uaServer, List<IoBaseEntity> drivers){
        driverService.addIoDrivers(uaServer, drivers);
        //TRUE TODO
        dataService.NoticeDriverAdded();
    }

    @RequestMapping(value="/del")
    public void deleteIoDrivers(String uaServer, List<IoBaseEntity> drivers){
        driverService.setMark(uaServer, drivers, Mark.DELETE);
        //driverService.deleteIoDrivers(uaServer, drivers);
        //TRUE TODO
        dataService.NoticeDriverDeleted();
    }

    /*//GTEST
    @RequestMapping(value="/add-test")
    public String addIoDriversTest(){
        List<IoBaseEntity> drivers = new ArrayList();
        drivers.add(new IoBaseEntity("IEC104TCP"));
        drivers.add(new IoBaseEntity("ModbusUdpServer"));
        addIoDrivers("ioserver", drivers);
        return "menu";
    }

    //GTEST
    @RequestMapping(value="/del-test")
    public String deleteIoDriversTest(){
        List<IoBaseEntity> drivers = new ArrayList();
        drivers.add(new IoBaseEntity("IEC104TCP"));
        drivers.add(new IoBaseEntity("ModbusUdpServer"));
        deleteIoDrivers("ioserver", drivers);
        return "menu";
    }*/
}
