package com.sunway.api;

import com.sunway.mapper.IIoDriverTypesMapper;
import com.sunway.model.IoDriverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-driver-type")
public class IoDriverTypesApi {

    @Autowired
    private IIoDriverTypesMapper ioDriverTypesMapper;

    @RequestMapping(value="/get")
    public List<IoDriverType> getDriverAllTypes(){
        return ioDriverTypesMapper.queryDriverTypes();
    }
}
