package com.sunway.api;

import com.sunway.model.IoAlarmConfig;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
import com.sunway.service.IoDeviceService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-device")
public class IoDeviceApi {

    @Autowired
    private IoDeviceService deviceService;

    //查询新增驱动
    @RequestMapping(value="/add")
    List<IoBaseEntity> queryAddedIoDevices(String channelName){
        List<IoBaseEntity> devices = deviceService.queryIoDevices(channelName, Mark.INSERT);
        deviceService.setMark(channelName, devices, Mark.DONE);
        return devices;
    }

    //查询删除驱动
    @RequestMapping(value="/del")
    public List<IoBaseEntity> queryDeletedDrivers(String channelName){
        List<IoBaseEntity> devices = deviceService.queryIoDevices(channelName, Mark.DELETE);
        deviceService.deleteIoDevices(channelName, devices);
        return devices;
    }

    //查询设备下变量
    @RequestMapping(value="/getvars")
    public List<IoVariable> queryVarsFromDevice(String device){
        return deviceService.queryVarsFromDevice(device);
    }

    //查询变量报警配置
    @RequestMapping(value="/getalarm")
    public List<IoAlarmConfig> queryVarAlarmConfig(String var){
        return deviceService.queryVarAlarmConfig(var);
    }

    //GTEST
    @RequestMapping(value="/getalarm-test")
    public List<IoAlarmConfig> queryVarAlarmConfigTest(){
        return queryVarAlarmConfig("pressure");
    }

    //GTEST
    @RequestMapping(value="/getvars-test")
    public List<IoVariable> queryVarsFromDeviceTest(){
        return queryVarsFromDevice("ModbusTcpClient.channel1.Blower");
    }

    //GTEST
    @RequestMapping(value="/add-test")
    public List<IoBaseEntity> queryIoDevices(){
        return queryAddedIoDevices("ModbusTcpClient.channel1");
    }

    @RequestMapping(value="/del-test")
    public List<IoBaseEntity> queryDeletedDriversTest(){
        return queryDeletedDrivers("ModbusTcpClient.channel1");
    }

}
