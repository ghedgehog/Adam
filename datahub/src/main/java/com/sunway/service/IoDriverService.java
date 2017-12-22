package com.sunway.service;

import com.sunway.exception.BusinessException;
import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoBaseEntity;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ioDriverService")
public class IoDriverService {

    @Autowired
    private IIoBaseMapper<IoBaseEntity> baseMapper;

    private String tableName = IIoTableName.IoDriver;
    private String ptableName = IIoTableName.IoUaServers;

    public int addIoDrivers(String uaServer, List<IoBaseEntity> drivers) throws BusinessException {
        try{
            return baseMapper.addBaseList(tableName, ptableName, uaServer, drivers);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<IoBaseEntity> queryIoDrivers(String uaServer, int mark){
        return baseMapper.queryIoBaseList(tableName, ptableName, uaServer, mark);
    }

    public void setMark(String uaServer, List<IoBaseEntity> drivers, int mark) throws BusinessException {
        try{
            baseMapper.setMark(tableName, ptableName, drivers, uaServer, mark);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void deleteIoDrivers(String uaServer, List<IoBaseEntity> drivers) throws BusinessException {
        try{
            baseMapper.deleteBaseList(tableName, ptableName, uaServer, drivers);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
