package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.service.IoDeviceService;
import com.sunway.service.RealDataService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value="/add")
    public  void addIoDevices(String channel, String template, List<IoBaseEntity> entityList){
        deviceService.addIoDevices(channel, template, entityList);
        //TRUE TODO
        dataService.NoticeDeviceAdded();
    }

    @RequestMapping(value="/del")
    public void deleteIoDevices(String channels, List<IoBaseEntity> entityList){
        //deviceService.deleteIoDevices(channels, entityList);
        deviceService.setMark(channels, entityList, Mark.DELETE);
        //TRUE TODO
        dataService.NoticeDeviceDeleted();
    }

    //GTest
    @RequestMapping(value="/add-test", method = RequestMethod.POST)
    public String addDevicesTest(@RequestBody Map<String, String> temp_map){
        for (Map.Entry<String,String> entry:
                temp_map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String device_name = temp_map.get("device_name");
        String channel_name = temp_map.get("channel_name");
        String driver_type = temp_map.get("driver_type");

        String channel_long_name = channel_name.concat(".").concat(device_name);
        String device_long_name = driver_type.concat(".").concat(channel_long_name);
        System.out.println(channel_long_name+"\t"+device_long_name);

        System.out.println("addDevicesTest...");
        List<IoBaseEntity> entities = new ArrayList();
        //IoBaseEntity entity = new IoBaseEntity("ModbusTcpClient.channel2.device2");
        IoBaseEntity entity = new IoBaseEntity(device_long_name);
        entities.add(entity);
        addIoDevices(channel_long_name, "BlowerTemplate", entities);
//        addIoDevices("ModbusTcpClient.channel1", "BlowerTemplate", entities);
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
