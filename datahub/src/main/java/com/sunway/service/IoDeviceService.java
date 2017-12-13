package com.sunway.service;

import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ioDeviceService")
public class IoDeviceService {

    @Autowired
    private IIoBaseMapper<IoBaseEntity> baseMapper;

    private String deviceTable = IIoTableName.IoDevice;
    private String channelTable = IIoTableName.IoChannel;
    private String tempTable = IIoTableName.IoDeviceTemplate;
    private String varTable = IIoTableName.IoDeviceTemplateVar;

    public void addIoDevices(String channelName,
                           String templateName,
                           List<IoBaseEntity> entityList) {
        baseMapper.addIoDevices(deviceTable,
                channelTable,
                tempTable,
                channelName,
                templateName,
                entityList);
    }

    public List<IoBaseEntity> queryIoDevices(String channelName, int mark){
        return  baseMapper.queryIoBaseList(deviceTable,channelTable, channelName, mark);
    }

    public void setMark(String channelName, List<IoBaseEntity> entityList, int mark){
        baseMapper.setMark(deviceTable, channelTable, entityList, channelName, mark);
    }

    public void deleteIoDevices(String channelName, List<IoBaseEntity> entityList){
        baseMapper.deleteBaseList(deviceTable, channelTable, channelName, entityList);
    }

    public List<IoVariable> queryVarsFromDevice(String device){
        return  baseMapper.queryVarsFromDevice(varTable, deviceTable, device);
    }
}
