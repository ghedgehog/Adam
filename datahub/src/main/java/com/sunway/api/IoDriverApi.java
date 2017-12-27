package com.sunway.api;

import com.sunway.exception.BusinessException;
import com.sunway.model.IoBaseEntity;
import com.sunway.service.IoDriverService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-driver")
public class IoDriverApi {

    @Autowired
    private IoDriverService driverService;

    //查询新增驱动
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public List<IoBaseEntity> queryAddedDrivers(String uaServer) throws BusinessException {
        List<IoBaseEntity> drivers = driverService.queryIoDrivers(uaServer, Mark.INSERT);
        if(drivers == null || drivers.isEmpty()) return null;
        driverService.setMark(uaServer, drivers, Mark.DONE);
        return drivers;
    }

    //查询删除驱动
    @RequestMapping(value="/del", method = RequestMethod.GET)
    public List<IoBaseEntity> queryDeletedDrivers(String uaServer) throws BusinessException {
        List<IoBaseEntity> drivers = driverService.queryIoDrivers(uaServer, Mark.DELETE);
        driverService.deleteIoDrivers(uaServer, drivers);
        return drivers;
    }

}
