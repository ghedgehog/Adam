package com.sunway.api;

import com.sunway.mapper.IIoDriverMapper;
import com.sunway.model.IoDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IoDriverApi {

    @Autowired
    private IIoDriverMapper ioDriverMapper;

    @RequestMapping(value="/api-get-drivers")
    public List<IoDriver> getDrivers(String uaServer){
        return ioDriverMapper.queryIoDrivers(uaServer);
    }
}
