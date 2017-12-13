package com.sunway.controller;

import com.sunway.service.RealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="real")
public class RealDataController {

    @Autowired
    RealDataService realDataService;

    @RequestMapping("/read")
    public String readRealData(String name){
        return realDataService.get(name);
    }

}
