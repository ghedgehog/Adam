package com.sunway.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/datahub")
public class RealDataApi {

    @RequestMapping("/login")
    public String login(){
        return "glogin text";
    }
}