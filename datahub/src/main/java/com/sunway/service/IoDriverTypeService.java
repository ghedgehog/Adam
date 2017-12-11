package com.sunway.service;

import com.sunway.mapper.IIoExtendMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoDriverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ioDriverTypeService")
public class IoDriverTypeService {

    @Autowired
    private IIoExtendMapper<IoDriverType> extendMapper;

    private String tableName = IIoTableName.IoDriverTypes;

    public List<IoDriverType> queryDriverTypes(){
        return extendMapper.queryDriverType(tableName);
    }
}
