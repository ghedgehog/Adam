package com.sunway.service.impl;

import com.sunway.mapper.IIoDriverTypesMapper;
import com.sunway.model.IoDriverType;
import com.sunway.service.IoDriverTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ioDriverTypeServiceImpl")
public class IoDriverTypeServiceImpl implements IoDriverTypeService{

    @Autowired
    private IIoDriverTypesMapper ioDriverTypesMapper;

    @Override
    public List<IoDriverType> queryDriverTypes() {
        return ioDriverTypesMapper.queryDriverTypes();
    }
}
