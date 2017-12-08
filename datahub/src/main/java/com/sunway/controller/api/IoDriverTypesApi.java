package com.sunway.controller.api;

import com.sunway.model.IoDriverType;
import com.sunway.service.impl.IoDriverTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IoDriverTypesApi {
    @Autowired
    private IoDriverTypeServiceImpl ioDriverTypeServiceImpl;

    @RequestMapping(value="/api-get-drivers-type")
    public List<IoDriverType> getDriverAllTypes(){
        return ioDriverTypeServiceImpl.queryDriverTypes();
    }
}
