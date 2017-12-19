package com.sunway.service;

import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoBaseEntity;
import com.sunway.utils.HisData;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ioDeviceTemplateService")
public class IoDeviceTemplateService {

    @Autowired
    private IIoBaseMapper<IoBaseEntity> baseMapper;

    private String templateTable = IIoTableName.IoDeviceTemplate;
    private String alarmTable = IIoTableName.IoDeviceTemplateAlarm;
    private String historyTable = IIoTableName.IoDeviceTemplateHistory;
    private String preStatisticsTable = IIoTableName.IoDeviceTemplatePreStatistics;
    private String varTable = IIoTableName.IoDeviceTemplateVar;

    public List<String> queryDeviceTemplate(){
        return baseMapper.querySysBaseList(templateTable);
    }

    public void addDeviceTempalte(List<IoBaseEntity> baseEntityList){
        baseMapper.addSysBaseList(templateTable, baseEntityList);
        for(IoBaseEntity entity: baseEntityList){
            IoBaseEntity entity1 = new IoBaseEntity("");
            List<IoBaseEntity> entityList = new ArrayList();
            entityList.add(entity1);
            //addIoDeviceTemplateAlarm(entity.getName(), entityList);
            addIoDeviceTemplateHistory(entity.getName(), entityList);
            addIoDeviceTemplatePreStaticstic(entity.getName(), entityList);
        }
    }

    public void deleteDeviceTempalte(List<IoBaseEntity> baseEntityList){
        baseMapper.setSysMark(templateTable, baseEntityList, Mark.DELETE);
        //baseMapper.deleteSysBaseList(tableName, baseEntityList);
    }

    public void addIoDeviceTemplateAlarm(String template, List<IoBaseEntity> entityList){
        baseMapper.addBaseList(alarmTable, templateTable, template, entityList);
    }

    public void deleteIoDeviceTemplateAlarm(String template, List<IoBaseEntity> entityList){
        baseMapper.setMark(alarmTable, templateTable, entityList, template, Mark.DELETE);
        //baseMapper.deleteBaseList(templateTable, alarmTable, template, entityList);
    }
    
    public void addIoDeviceTemplateHistory(String template, List<IoBaseEntity> entityList){
        baseMapper.addBaseList(historyTable, templateTable,  template, entityList);
    }

    public void deleteIoDeviceTemplateHistory(String template, List<IoBaseEntity> entityList){
        baseMapper.setMark(historyTable, templateTable,  entityList, template, Mark.DELETE);
        //baseMapper.deleteBaseList(tableName, ptableName, template, entityList);
    }

    public void addIoDeviceTemplatePreStaticstic(String template, List<IoBaseEntity> entityList){
        baseMapper.addBaseList(preStatisticsTable, templateTable,  template, entityList);
    }

    public void deleteIoDeviceTemplatePreStaticstic(String template, List<IoBaseEntity> entityList){
        baseMapper.setMark(preStatisticsTable, templateTable,  entityList, template, Mark.DELETE);
        //baseMapper.deleteBaseList(tableName, ptableName, template, entityList);
    }

    public void addIoDeviceTemplateVar(String template, List<IoBaseEntity> entityList){
        baseMapper.addBaseList(varTable, templateTable,  template, entityList);
    }

    public void deleteIoDeviceTemplateVar(String template, List<IoBaseEntity> entityList){
        baseMapper.setMark(varTable, templateTable,  entityList, template, Mark.DELETE);
        //baseMapper.deleteBaseList(tableName, ptableName, template, entityList);
    }
}
