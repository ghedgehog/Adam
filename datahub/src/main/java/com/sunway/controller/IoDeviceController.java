package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoChannel;
import com.sunway.service.IoChannelService;
import com.sunway.service.IoDeviceService;
import com.sunway.service.IoDriverService;
import com.sunway.service.RealDataService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="device")
public class IoDeviceController {

    @Autowired
    private IoDeviceService deviceService;

    @Autowired
    private RealDataService dataService;

    @Autowired
    private IoChannelService channelService;

    @Autowired
    private IoDriverService driverService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public  boolean addIoDevices(@RequestBody Map<String, String> tempMap){

        String deviceName = tempMap.get("device_name");
        String channelName = tempMap.get("channel_name");
        String driverType = tempMap.get("driver_type");
        String deviceModel = tempMap.get("device_model");

        //非空校验 TODO
        String channelLongName = driverType.concat(".").concat(channelName);
        String deviceLongName = channelLongName.concat(".").concat(deviceName);

        if(!addDriver(driverType) || !addChannel(driverType, channelLongName)) return false; //throw exception

        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity(deviceLongName);
        entities.add(entity);

        deviceService.addIoDevices(channelLongName, deviceModel, entities);
        //TRUE TODO
        dataService.NoticeDeviceAdded();
        dataService.NoticeVariableAdded();

        return true;
    }

    @RequestMapping(value="/del")
    public void deleteIoDevices(String channels, List<IoBaseEntity> entityList){
        //deviceService.deleteIoDevices(channels, entityList);
        deviceService.setMark(channels, entityList, Mark.DELETE);
        //TRUE TODO
        dataService.NoticeDeviceDeleted();
    }

    @RequestMapping(value="/get", method = RequestMethod.GET)
    @ResponseBody
    public List<IoBaseEntity> queryAllIoDevices(){
        return deviceService.queryAllIoDevices();
    }

    /*//GTest
    @RequestMapping(value="/del-test")
    public String deleteIoDevicesTest(){
        *//*List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("ModbusTcpClient.channel1.device1");
        entities.add(entity);
        deleteIoDevices("ModbusTcpClient.channel1", entities);*//*
        return "menu";
    }*/

    private boolean addChannel(String driver_type, String channel_long_name){
        if(channel_long_name==null) return false;

        List<IoChannel> channels = channelService.queryIoChannelsByMark(driver_type, -1);
        IoChannel channel = new IoChannel(channel_long_name);

        //if(channels.contains(channel)) return true;
        if(!channels.isEmpty() && ((IoBaseEntity)channels.get(0)).getName().equals(channel_long_name)) return true;

        channels.clear();
        channels.add(channel);
        channelService.addIoChannels(driver_type, channels);

        return true;
    }

    private boolean addDriver(String driver_type){
        if(driver_type==null) return false;
        String uaServer = "ioserver";

        IoBaseEntity entity = new IoBaseEntity(driver_type);
        List<IoBaseEntity> entities = driverService.queryIoDrivers(uaServer, -1);

        //if(entities.contains(entity)) return true;
        if(!entities.isEmpty() && entities.get(0).getName().equals(driver_type)) return true;

        entities.clear();
        entities.add(entity);
        driverService.addIoDrivers(uaServer, entities);
        return true;
    }
}
