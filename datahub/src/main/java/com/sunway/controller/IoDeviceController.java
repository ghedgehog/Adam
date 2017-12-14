package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.service.IoDeviceService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="device")
public class IoDeviceController {

    @Autowired
    private IoDeviceService deviceService;

    @RequestMapping(value="/add")
    public  void addIoDevices(String channel, String template, List<IoBaseEntity> entityList){
        deviceService.addIoDevices(channel, template, entityList);
    }

    @RequestMapping(value="/del")
    public void deleteIoDevices(String channels, List<IoBaseEntity> entityList){
        //deviceService.deleteIoDevices(channels, entityList);
        deviceService.setMark(channels, entityList, Mark.DELETE);
    }

    //GTest
    @RequestMapping(value="/add-test")
    public String addDevicesTest(){
        System.out.println("addDevicesTest...");
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("ModbusTcpClient.channel1.myDevice");
        entities.add(entity);
        addIoDevices("ModbusTcpClient.channel1", "mytemplate", entities);
        return "menu";
    }

    //GTest
    @RequestMapping(value="/del-test")
    public String deleteIoDevicesTest(){
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("ModbusTcpClient.channel1.device1");
        entities.add(entity);
        deleteIoDevices("ModbusTcpClient.channel1", entities);
        return "menu";
    }
}
