package com.sunway.service;

import com.sunway.exception.BusinessException;
import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoTableName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IoConfigChangeService {

    private String tableName = IIoTableName.IoUpdate;

    @Autowired
    private IIoBaseMapper baseMapper;

    public int getUpdateMaxValue() throws BusinessException {
        try{
            return baseMapper.queryUpdateMaxValue(tableName);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public List<String> getUpdateTablesByVersion(int version) throws BusinessException {
        try{
            return baseMapper.queryUpdateTablesByVersion(tableName, version);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
