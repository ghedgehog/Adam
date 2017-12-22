package com.sunway.controller;

import com.sunway.service.RealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value="real")
public class RealDataController {

    @Autowired
    RealDataService realDataService;

    @RequestMapping(value = "/read-obj", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> readDeviceRealData(String dviceName){
        return realDataService.queryRealDataByDevice(dviceName);
    }

    @RequestMapping(value = "/read-var", method = RequestMethod.GET)
    @ResponseBody
    public Object readVarRealData(String varName){
        return realDataService.queryRealDataByVar(varName);
    }

    /*@RequestMapping(value = "/read-obj-test", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> readDeviceRealDataTest(){
        return readDeviceRealData("FORCE_HLS_SIM.channel.Blower");
    }*/
}
