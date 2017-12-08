package com.sunway.controller;

import com.sunway.mapper.IIoDriverTypesMapper;
import com.sunway.model.IoDriverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class IoDriverTypesController {

    @Autowired
    private IIoDriverTypesMapper ioDriverTypesMapper;

    @RequestMapping(value = "/get-drivers")
    public String getDriverAllTypes() {
        List<IoDriverType> ioDriverTypes = ioDriverTypesMapper.queryDriverTypes();
        //FORTEST
        for (IoDriverType driver : ioDriverTypes) {
            System.out.println("Name:" + driver.getName()
                    + ", Description" + driver.getDescription()
                    + ", Communication:" + driver.getCommunication_type());
        }
        return "menu";
    }
}
