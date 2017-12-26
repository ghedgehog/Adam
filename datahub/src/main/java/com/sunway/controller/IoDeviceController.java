package com.sunway.controller;

import com.sunway.exception.BusinessException;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoChannel;
import com.sunway.model.IoDevice;
import com.sunway.service.IoChannelService;
import com.sunway.service.IoDeviceService;
import com.sunway.service.IoDriverService;
import com.sunway.service.RealDataService;
import com.sunway.utils.Mark;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    //IoBaseEntity设备模板需单独定义model，为演示暂时先在IoBaseEntity中增加propConf属性 TODO
    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public  String addIoDevices(@RequestBody Map<String, String> deviceMap) throws BusinessException, InterruptedException {
        System.out.println("addIoDevices()");
        for (Map.Entry<String, String> entry : deviceMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String deviceName = deviceMap.get("device_name");
        String channelName = deviceMap.get("channel_name");
        String driverType = deviceMap.get("driver_type");
        String deviceModel = deviceMap.get("device_model");
        String propConf = getPropConf(deviceMap);
        //非空校验 TODO
        String channelLongName = driverType.concat(".").concat(channelName);
        String deviceLongName = channelLongName.concat(".").concat(deviceName);

        if(!addDriver(driverType) || !addChannel(driverType, channelLongName)) return null;
        List<IoDevice> entities = new ArrayList();
        IoDevice entity = new IoDevice();
        entity.setName(deviceLongName);
        entity.setPropConf(propConf);
        entity.setDescription(deviceLongName);
        entities.add(entity);

        deviceService.addIoDevices(channelLongName, deviceModel, entities);
        //TRUE TODO
        dataService.NoticeDeviceAdded();
        Thread.sleep(3000);
        dataService.NoticeVariableAdded();

        return "Ok";
    }

    @RequestMapping(value="/del")
    public void deleteIoDevices(String channels, List<IoBaseEntity> entityList){
        //deviceService.deleteIoDevices(channels, entityList);
        deviceService.setMark(channels, entityList, Mark.DELETE);
        //TRUE TODO
        dataService.NoticeDeviceDeleted();
    }

    private String getDeviceValueString(String deviceStr){
        if(deviceStr == null || deviceStr.isEmpty())
            return "";
        else
            return deviceStr;
    }

    @RequestMapping(value="/get", method = RequestMethod.GET)
    @ResponseBody
    public List<IoBaseEntity> queryAllIoDevices(){
        return deviceService.queryAllIoDevices();
    }

    //IOCHANNEL  IoBaseEntity 定义模板 TODO
    private boolean isExistsChannelName(List<IoChannel> channels, String name){
        for(IoBaseEntity entity : channels){
            if (entity.getName().equals(name)) return true;
        }
        return false;
    }

    private boolean addChannel(String driver_type, String channel_long_name) throws BusinessException {
        if(channel_long_name==null) return false;

        List<IoChannel> channels = channelService.queryIoChannelsByMark(driver_type, -1);

        if(!channels.isEmpty() && isExistsChannelName(channels, channel_long_name)) return true;

        IoChannel channel = new IoChannel(channel_long_name);
        channels.clear();
        channels.add(channel);

        int ret = channelService.addIoChannels(driver_type, channels);

        if (ret!=0){
            dataService.NoticeChannelAdded();
            return true;
        }
        return false;
    }

    private boolean isExistsDriverName(List<IoBaseEntity> baseEntities, String name){
        for(IoBaseEntity entity : baseEntities){
            if (entity.getName().equals(name)) return true;
        }
        return false;
    }

    private boolean addDriver(String driver_type) throws BusinessException {
        if(driver_type==null) return false;
        String uaServer = "ioserver";

        List<IoBaseEntity> entities = driverService.queryIoDrivers(uaServer, -1);

        if(!entities.isEmpty() && isExistsDriverName(entities, driver_type)){ return true; }

        IoBaseEntity entity = new IoBaseEntity(driver_type);
        entities.clear();
        entities.add(entity);
        int ret = driverService.addIoDrivers(uaServer, entities);

        if (ret!=0){
            dataService.NoticeDriverAdded();
            return true;
        }
        return false;
    }

    private String getPropConf(Map<String, String> deviceMap){
        Document doc = DocumentHelper.createDocument();
        Element rootEle = doc.addElement("Values");


        Element subRoot = rootEle.addElement("Config");
        Element thirdRoot = subRoot.addElement("Values");

        thirdRoot.addElement("Address").setText(getDeviceValueString(deviceMap.get("Address")));
        thirdRoot.addElement("Port").setText( getDeviceValueString(deviceMap.get("Port")));
        thirdRoot.addElement("BackupAddress").setText( getDeviceValueString(deviceMap.get("BackupAddress")));
        thirdRoot.addElement("BackupPort").setText(getDeviceValueString(deviceMap.get("BackupPort")));
        thirdRoot.addElement("ProtocolType").setText( getDeviceValueString(deviceMap.get("ProtocolType")));
        thirdRoot.addElement("DeviceAddress").setText( getDeviceValueString(deviceMap.get("DeviceAddress")));
        thirdRoot.addElement("MaxPackageLen").setText( getDeviceValueString(deviceMap.get("MaxPackageLen")));
        thirdRoot.addElement("PackageSpace").setText(getDeviceValueString(deviceMap.get("PackageSpace")));
        thirdRoot.addElement("LongType").setText( getDeviceValueString(deviceMap.get("LongType")));
        thirdRoot.addElement("FloatType").setText( getDeviceValueString(deviceMap.get("FloatType")));
        thirdRoot.addElement("AddrOrder").setText( getDeviceValueString(deviceMap.get("AddrOrder")));
        thirdRoot.addElement("CountOrder").setText(getDeviceValueString( deviceMap.get("CountOrder")));
        thirdRoot.addElement("DataOrder").setText( getDeviceValueString(deviceMap.get("DataOrder")));
        thirdRoot.addElement("DoubleOrder").setText( getDeviceValueString(deviceMap.get("DoubleOrder")));
        thirdRoot.addElement("CheckOrder").setText( getDeviceValueString(deviceMap.get("CheckOrder")));

        // TODO
        String propConf = doc.asXML();
        return propConf;
    }
}
