package com.sunway.api;

import com.sunway.model.IoDriverType;
import com.sunway.service.IoDriverTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-driver-type")
public class IoDriverTypesApi {

    @Autowired
    private IoDriverTypeService driverTypeService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<IoDriverType> getDriverAllTypes() {
        return driverTypeService.queryDriverTypes();
    }
}
