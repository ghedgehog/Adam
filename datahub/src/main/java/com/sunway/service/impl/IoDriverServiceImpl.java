package com.sunway.service.impl;

import com.sunway.mapper.IIoDriverMapper;
import com.sunway.model.IoDriver;
import com.sunway.service.IoDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;

@Service("ioDriverServiceImpl")
public class IoDriverServiceImpl implements IoDriverService{

    @Autowired
    private IIoDriverMapper ioDriverMapper;

    @Override
    public void addIoDrivers(String uaServer, List<IoDriver> drivers) {
        ioDriverMapper.addIoDrivers(uaServer, drivers);
    }

    @Override
    public List<IoDriver> queryIoDrivers(String uaServer) {
        return ioDriverMapper.queryIoDrivers(uaServer);
    }
}
