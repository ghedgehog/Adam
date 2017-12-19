package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.service.IoDeviceTemplateService;
import com.sunway.service.RealDataService;
import com.sunway.utils.DataTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "dev-template")
public class IoDeviceTemplateController {

    @Autowired
    private IoDeviceTemplateService deviceTemplateService;

    @RequestMapping(value = "/get")
    @ResponseBody
    public  List<String> getAllDeviceTemplates() {
        return deviceTemplateService.queryDeviceTemplate();
    }

    @RequestMapping(value = "/add")
    public String addDeviceTempalte(@RequestBody Map<String, String> temp_map) {

        String createTime = temp_map.get("create-time");
        String modifyTime = temp_map.get("modify-time");

        System.out.println(createTime + ", " + modifyTime + ", " + temp_map.get("descript"));

        IoBaseEntity entity = new IoBaseEntity(temp_map.get("model-name"));

        if(createTime != null)
            entity.setCreateTime(DataTool.getDateFromString(createTime));
        if(modifyTime != null)
            entity.setCreateTime(DataTool.getDateFromString(modifyTime));

        entity.setDescription(temp_map.get("descript"));

        List<IoBaseEntity> baseEntityList = new ArrayList<IoBaseEntity>();

        baseEntityList.add(entity);

        deviceTemplateService.addDeviceTempalte(baseEntityList);
        return "menu";
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
        /*//创建模板
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("mytemplate");
        entities.add(entity);
        //addDeviceTempalte(entities);
        //创建变量
        List<IoBaseEntity> varlist = new ArrayList();
        varlist.add(new IoBaseEntity("pressure"));
        varlist.add(new IoBaseEntity("temperature"));
        varlist.add(new IoBaseEntity("water_volume"));
        addDeviceTempalteVar("mytemplate", varlist);*/
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
