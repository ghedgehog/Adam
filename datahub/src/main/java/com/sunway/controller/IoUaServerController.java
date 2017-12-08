package com.sunway.controller;

import com.sunway.mapper.IIoUaServerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="server")
public class IoUaServerController {

    @Autowired
    private IIoUaServerMapper ioUaServerMapper;

    @RequestMapping(value="/")
    public String addIoUaServer(String uaServer){
        ioUaServerMapper.addIoUaServer(uaServer);
        return "menu";
    }
}
