package com.sunway.controller;

import com.sunway.service.RealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "real")
public class RealDataController {

    @Autowired
    RealDataService realDataService;

    @RequestMapping(value = "/read-obj", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> readDeviceRealData(@RequestParam("device") String device) {
        System.out.println("传入进来的设备名称：" + device);
        Map<String, Object> temp = realDataService.queryRealDataByDevice(device);
        if (temp == null) return null;
        for (Map.Entry<String, Object> entry : temp.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return temp;
    }

    @RequestMapping(value = "/read-var", method = RequestMethod.GET)
    @ResponseBody
    public Object readVarRealData(String varName) {
        return realDataService.queryRealDataByVar(varName);
    }

    /*@RequestMapping(value = "/read-obj-test", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> readDeviceRealDataTest(){
        return readDeviceRealData("FORCE_HLS_SIM.channel.Blower");
    }*/
}
