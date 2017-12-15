package com.sunway.controller;


import com.sunway.model.IoDriverType;
import com.sunway.service.IoDriverTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value="drivertype")
public class IoDriverTypesController {

    @Autowired
    private IoDriverTypeService driverTypeService;

    @RequestMapping(value = "/get")
    @ResponseBody
    public  List<String> getDriverAllTypes(Model model) {
        List<IoDriverType> ioDriverTypes = driverTypeService.queryDriverTypes();
        //FORTEST
        List<String> list = new ArrayList<String>();
        String Name = ioDriverTypes.get(2).getName();
        System.out.println(Name);
        for (IoDriverType driver : ioDriverTypes) {
            list.add(driver.getName());
//            System.out.println("Name:" + driver.getName()
//                    + ", Description:" + driver.getDescription()
//                    + ", Communication:" + driver.getCommunicationType());
        }
        return list;
    }
}
