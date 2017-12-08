package com.sunway.service;

import com.sunway.model.IoDriver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IoDriverService {

    //添加驱动
    public void addIoDrivers(String uaServer, @Param("list")List<IoDriver> drivers);

    //查询新增驱动
    public List<IoDriver> queryIoDrivers(String uaServer);
}
