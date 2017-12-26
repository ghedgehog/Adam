package com.sunway.controller;

import com.sunway.exception.BusinessException;
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
    public void addIoDrivers(String uaServer, List<IoBaseEntity> drivers) throws BusinessException {
        driverService.addIoDrivers(uaServer, drivers);
        //TRUE TODO
        dataService.NoticeDriverAdded();
    }

    @RequestMapping(value="/del")
    public void deleteIoDrivers(String uaServer, List<IoBaseEntity> drivers) throws BusinessException {
        driverService.setMark(uaServer, drivers, Mark.DELETE);
        //driverService.deleteIoDrivers(uaServer, drivers);
        //TRUE TODO
        dataService.NoticeDriverDeleted();
    }
}
