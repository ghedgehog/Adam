package com.sunway.controller;

import com.sunway.model.IoDriverType;
import com.sunway.service.impl.IoDriverTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class IoDriverTypesController {

    @Autowired
    private IoDriverTypeServiceImpl ioDriverTypeServiceImpl;

    @RequestMapping(value="/get-drivers")
    public List<IoDriverType> getDriverAllTypes(){
        return ioDriverTypeServiceImpl.queryDriverTypes();
    }
}
