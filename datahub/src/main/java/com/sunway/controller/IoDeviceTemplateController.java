package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.service.IoDeviceTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "dev-template")
public class IoDeviceTemplateController {

    @Autowired
    private IoDeviceTemplateService deviceTemplateService;

    @RequestMapping(value = "/add")
    public void addDeviceTempalte(List<IoBaseEntity> baseEntityList) {
        deviceTemplateService.addDeviceTempalte(baseEntityList);
    }

    @RequestMapping(value = "/del")
    public void deleteDeviceTempalte(List<IoBaseEntity> baseEntityList) {
        deviceTemplateService.deleteDeviceTempalte(baseEntityList);
    }

    @RequestMapping(value = "/addvar")
    public void addDeviceTempalteVar(String template, List<IoBaseEntity> entityList) {
        deviceTemplateService.addIoDeviceTemplateVar(template, entityList);
    }

    @RequestMapping(value = "/delvar")
    public void deleteDeviceTempalteVar(String template, List<IoBaseEntity> entityList) {
        deviceTemplateService.deleteIoDeviceTemplateVar(template, entityList);
    }

    @RequestMapping(value = "/add-test")
    public String addDeviceTempalteTest() {
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("mytemplate");
        entities.add(entity);
        addDeviceTempalte(entities);
        return "menu";
    }

    @RequestMapping(value = "/del-test")
    public String delDeviceTempalteTest() {
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("mytemplate");
        entities.add(entity);
        deleteDeviceTempalte(entities);
        return "menu";
    }

}
