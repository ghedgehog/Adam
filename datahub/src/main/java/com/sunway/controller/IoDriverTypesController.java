package com.sunway.controller;

import com.sunway.model.IoDriverType;
import com.sunway.service.IoDriverTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(value="drivertype")
public class IoDriverTypesController {

    @Autowired
    private IoDriverTypeService driverTypeService;

    @RequestMapping(value = "/get")
    public String getDriverAllTypes() {
        List<IoDriverType> ioDriverTypes = driverTypeService.queryDriverTypes();
        //FORTEST
        for (IoDriverType driver : ioDriverTypes) {
            System.out.println("Name:" + driver.getName()
                    + ", Description" + driver.getDescription()
                    + ", Communication:" + driver.getCommunicationType());
        }
        return "menu";
    }
}
