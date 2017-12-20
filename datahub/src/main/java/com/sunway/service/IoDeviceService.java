package com.sunway.service;

import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoAlarmConfig;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
import com.sunway.utils.HisData;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ioDeviceService")
public class IoDeviceService {

    private String deviceTable = IIoTableName.IoDevice;
    private String channelTable = IIoTableName.IoChannel;
    private String tempTable = IIoTableName.IoDeviceTemplate;
    private String varTable = IIoTableName.IoDeviceTemplateVar;
    private String alarmTable = IIoTableName.IoDeviceAlarm;

    @Autowired
    private IIoBaseMapper<IoBaseEntity> baseMapper;

    @Autowired
    private RealDataService realDataService;

    @Autowired
    private IIoHisDataMapper hisDataMapper;

    /*@Autowired
    public IoDeviceService() {
        //更新设备至实时库
        updateDeviceToRealHub();
    }*/
    public void init(){
        updateDeviceToRealHub();
    }

    public void addIoDevices(String channelName, String templateName, List<IoBaseEntity> entityList) {

        baseMapper.addIoDevices(deviceTable, channelTable, tempTable, channelName, templateName, entityList);

        //创建历史表
        for (IoBaseEntity entity : entityList){
            createHistroyTable(templateName, entity.getName());
        }
        //更新设备至实时库
        updateDeviceToRealHub();
    }

    public List<IoBaseEntity> queryAllIoDevices(){
        return  baseMapper.queryAllIoBase(deviceTable);
    }

    public List<String> getBaseName(List<IoBaseEntity> baseEntities){
        List<String> nameList = new ArrayList<String>();
        for(IoBaseEntity entity: baseEntities){
            nameList.add(entity.getName());
        }
        return  nameList;
    }

    public List<IoBaseEntity> queryIoDevices(String channelName, int mark){
        return  baseMapper.queryIoBaseList(deviceTable,channelTable, channelName, mark);
    }

    public void setMark(String channelName, List<IoBaseEntity> entityList, int mark){
        baseMapper.setMark(deviceTable, channelTable, entityList, channelName, mark);
    }

    public void deleteIoDevices(String channelName, List<IoBaseEntity> entityList){
        baseMapper.deleteBaseList(deviceTable, channelTable, channelName, entityList);
        updateDeviceToRealHub();
    }

    public List<IoVariable> queryVarsFromDevice(String device){
        return baseMapper.queryVarsFromDevice(varTable, deviceTable, device);
    }

    public List<IoAlarmConfig> queryAlarmConfig(int mark){
        List<IoAlarmConfig> alarmConfigList = baseMapper.queryAlarmConfig(alarmTable, mark);
        setMarkToAlarmConfig(alarmConfigList);
        return alarmConfigList;
    }

    public List<IoAlarmConfig> queryAlarmConfigByVar(String var){
        return baseMapper.queryAlarmConfigByVar(alarmTable, varTable, var);
    }

    public List<String> queryVariableNames(String device){
        List<String> varNames = new ArrayList<String>();
        List<IoVariable> variables = queryVarsFromDevice(device);
        for (IoVariable variable : variables){
            varNames.add(variable.getName());
        }
        return varNames;
    }

    private void setMarkToAlarmConfig(List<IoAlarmConfig> alarmConfigList){
        List<IoBaseEntity> entities = new ArrayList<IoBaseEntity>();
        for(IoAlarmConfig config : alarmConfigList){
            IoBaseEntity entity = new IoBaseEntity(config.getName());
            entities.add(entity);
        }
        baseMapper.setSysMark(alarmTable, entities, Mark.DONE);
    }

    //更新至实时库
    private void updateDeviceToRealHub(){
        List<String> devList = getBaseName(queryAllIoDevices());
        System.out.println( "queryAllIoDevices: " + devList);
        if(devList.isEmpty()) return;
        realDataService.updateAllDevices(devList);
        for(String device : devList){
            List<String> varList = queryVariableNames(device);
            realDataService.updateVariables(device, varList);
        }
    }

    private boolean createHistroyTable(String template, String deviceName){
        //创建历史表 TODO
        String templateHis = HisData.hisTablePrefix + template;

        //SELECT * FROM pg_class WHERE relname='templatehis' 表名作为变量小写
        int ret = baseMapper.isTableExists(templateHis.toLowerCase());
        if(ret > 0) return true;

        List<IoVariable> entityList = queryVarsFromDevice(deviceName);
        if(entityList.isEmpty()) return  false;

        List<String> varName = new ArrayList();
        for(IoVariable entity : entityList){
            varName.add(entity.getName());
        }
        hisDataMapper.crateHisTableByTemplate(templateHis, varName);
        return true;
    }
}
