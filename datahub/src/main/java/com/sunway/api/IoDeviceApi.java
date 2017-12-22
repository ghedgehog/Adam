package com.sunway.api;

import com.sunway.model.IoAlarmConfig;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
import com.sunway.service.IoDeviceService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-device")
public class IoDeviceApi {

    @Autowired
    private IoDeviceService deviceService;

    //查询新增驱动
    @RequestMapping(value="/add", method = RequestMethod.GET)
    List<IoBaseEntity> queryAddedIoDevices(String channelName){
        List<IoBaseEntity> devices = deviceService.queryIoDevices(channelName, Mark.INSERT);
        if(devices==null || devices.isEmpty()) return null;
        deviceService.setMark(channelName, devices, Mark.DONE);
        return devices;
    }

    //查询删除驱动
    @RequestMapping(value="/del", method = RequestMethod.GET)
    public List<IoBaseEntity> queryDeletedDrivers(String channelName){
        List<IoBaseEntity> devices = deviceService.queryIoDevices(channelName, Mark.DELETE);
        deviceService.deleteIoDevices(channelName, devices);
        return devices;
    }

    //查询设备下变量
    @RequestMapping(value="/var", method = RequestMethod.GET)
    public List<IoVariable> queryVarsFromDevice(String device){
        return deviceService.queryVarsFromDevice(device);
    }

    //查询变量报警配置
    @RequestMapping(value="/alm-conf-var", method = RequestMethod.GET)
    public List<IoAlarmConfig> queryAlarmConfigByVar(String var){
        return deviceService.queryAlarmConfigByVar(var);
    }

    //查询所有新增报警对象
    @RequestMapping(value="/alm-conf", method = RequestMethod.GET)
    public List<IoAlarmConfig> queryAlarmConfig(){
        return deviceService.queryAlarmConfig(Mark.INSERT);
    }

    /*//GTEST
    @RequestMapping(value="/alm-conf-var-test")
    public List<IoAlarmConfig> queryAlarmConfigByVarTest(){
        return queryAlarmConfigByVar("pressure");
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
*/
}
