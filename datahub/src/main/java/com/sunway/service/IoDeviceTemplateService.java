package com.sunway.service;

import com.sunway.exception.BusinessException;
import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
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
    private String alarmTable = IIoTableName.IoDeviceAlarm;
    private String historyTable = IIoTableName.IoDeviceTemplateHistory;
    private String preStatisticsTable = IIoTableName.IoDeviceTemplatePreStatistics;
    private String varTable = IIoTableName.IoDeviceTemplateVar;

    public List<String> queryDeviceTemplate(){
        return baseMapper.querySysBaseList(templateTable);
    }

    public void addDeviceTempalte(List<IoBaseEntity> baseEntityList) throws BusinessException {
        try{
            baseMapper.addSysBaseList(templateTable, baseEntityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }

        for(IoBaseEntity entity: baseEntityList){
            IoBaseEntity entity1 = new IoBaseEntity("");
            List<IoBaseEntity> entityList = new ArrayList();
            entityList.add(entity1);
            //addIoDeviceTemplateAlarm(entity.getName(), entityList);
            addIoDeviceTemplateHistory(entity.getName(), entityList);
            addIoDeviceTemplatePreStaticstic(entity.getName(), entityList);
        }
    }

    public void deleteDeviceTempalte(List<IoBaseEntity> baseEntityList) throws BusinessException {
        try{
            baseMapper.setSysMark(templateTable, baseEntityList, Mark.DELETE);
            //baseMapper.deleteSysBaseList(tableName, baseEntityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void addIoDeviceTemplateAlarm(String template, List<IoBaseEntity> entityList) throws BusinessException {
        try{
            baseMapper.addBaseList(alarmTable, templateTable, template, entityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void deleteIoDeviceTemplateAlarm(String template, List<IoBaseEntity> entityList) throws BusinessException {
        try{
            baseMapper.setMark(alarmTable, templateTable, entityList, template, Mark.DELETE);
            //baseMapper.deleteBaseList(templateTable, alarmTable, template, entityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    
    public void addIoDeviceTemplateHistory(String template, List<IoBaseEntity> entityList) throws BusinessException {
        try{
            baseMapper.addBaseList(historyTable, templateTable,  template, entityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void deleteIoDeviceTemplateHistory(String template, List<IoBaseEntity> entityList) throws BusinessException {
        try{
            baseMapper.setMark(historyTable, templateTable,  entityList, template, Mark.DELETE);
            //baseMapper.deleteBaseList(tableName, ptableName, template, entityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void addIoDeviceTemplatePreStaticstic(String template, List<IoBaseEntity> entityList){
        //baseMapper.addBaseList(preStatisticsTable, templateTable,  template, entityList);
    }

    public void deleteIoDeviceTemplatePreStaticstic(String template, List<IoBaseEntity> entityList){
        //baseMapper.setMark(preStatisticsTable, templateTable,  entityList, template, Mark.DELETE);
        //baseMapper.deleteBaseList(tableName, ptableName, template, entityList);
    }

    public void addIoDecieTemplateVars(String template, String alarm, List<IoVariable> variableList) throws BusinessException {
        try{
            baseMapper.addIoVariables(varTable, templateTable, alarmTable, template, alarm, variableList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void deleteIoDeviceTemplateVar(String template, List<IoBaseEntity> entityList) throws BusinessException {
        try{
            baseMapper.setMark(varTable, templateTable,  entityList, template, Mark.DELETE);
            //baseMapper.deleteBaseList(tableName, ptableName, template, entityList);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<IoVariable> queryVarsByTemplate(String template){
        return baseMapper.queryVarsFromTemplate(varTable, templateTable, template);
    }
}
