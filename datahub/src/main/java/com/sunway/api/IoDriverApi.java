package com.sunway.api;

import com.sunway.mapper.IIoDriverMapper;
import com.sunway.model.IoDriver;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="api-driver")
public class IoDriverApi {

    @Autowired
    private IIoDriverMapper ioDriverMapper;

    //查询新增驱动
    @RequestMapping(value="/add")
    public List<IoDriver> queryAddedDrivers(String uaServer){
        List<IoDriver> drivers = ioDriverMapper.queryIoDrivers(uaServer, Mark.INSERT);
        ioDriverMapper.setMark(drivers, Mark.DONE);
        return drivers;
    }

    //查询删除驱动
    @RequestMapping(value="/del")
    public List<IoDriver> queryDeletedDrivers(String uaServer){
        List<IoDriver> drivers = ioDriverMapper.queryIoDrivers(uaServer, Mark.DELETE);
        ioDriverMapper.deleteIoDrivers(uaServer, drivers);
        return drivers;
    }
}
