package com.sunway.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/datahub")
public class IoRealDataApi {

    @RequestMapping("/conf-event")
    public String setRealData(){
        return "JSON...";
    }
}