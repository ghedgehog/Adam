package com.sunway.controller;

import com.sunway.mapper.IIoDriverMapper;
import com.sunway.model.IoDriver;
import com.sunway.utils.Mark;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value="driver")
public class IoDriverController {

    @Autowired
    private IIoDriverMapper ioDriverMapper;

    @RequestMapping(value="/add")
    public void addIoDrivers(String uaServer, List<IoDriver> drivers){
        ioDriverMapper.addIoDrivers(uaServer, drivers);
    }

    @RequestMapping(value="/del")
    public void deleteIoDrivers(List<IoDriver> drivers){
        ioDriverMapper.setMark(drivers, Mark.DELETE);
    }

    //GTEST
    @RequestMapping(value="/add-test")
    public String addIoDriversTest(){
        List<IoDriver> drivers = new ArrayList();
        drivers.add(new IoDriver("IEC104TCP"));
        drivers.add(new IoDriver("ModbusUdpServer"));
        addIoDrivers("ioserver", drivers);
        return "menu";
    }

    //GTEST
    @RequestMapping(value="/del-test")
    public String deleteIoDriversTest(){
        List<IoDriver> drivers = new ArrayList();
        drivers.add(new IoDriver("IEC104TCP"));
        drivers.add(new IoDriver("ModbusUdpServer"));
        deleteIoDrivers(drivers);
        return "menu";
    }
}
