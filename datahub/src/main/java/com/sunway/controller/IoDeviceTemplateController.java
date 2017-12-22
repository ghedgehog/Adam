package com.sunway.controller;

import com.sunway.exception.BusinessException;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
import com.sunway.service.IoDeviceTemplateService;
import com.sunway.utils.JsonTool;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String addDeviceTempalte(@RequestBody Map<String, String> temp_map) throws BusinessException {

        String createTime = temp_map.get("create-time");
        String modifyTime = temp_map.get("modify-time");

        IoBaseEntity entity = new IoBaseEntity(temp_map.get("model-name"));

        if(!createTime.isEmpty())
            entity.setCreateTime(JsonTool.getDateAndTimeFromString(createTime));
        if(!modifyTime.isEmpty())
            entity.setCreateTime(JsonTool.getDateAndTimeFromString(modifyTime));

        entity.setDescription(temp_map.get("descript"));

        List<IoBaseEntity> baseEntityList = new ArrayList<IoBaseEntity>();

        baseEntityList.add(entity);

        deviceTemplateService.addDeviceTempalte(baseEntityList);
        return "menu";
    }

    @RequestMapping(value = "/del")
    public void deleteDeviceTempalte(List<IoBaseEntity> baseEntityList) throws BusinessException {
        deviceTemplateService.deleteDeviceTempalte(baseEntityList);
    }

    @RequestMapping(value = "/add-var")
    @ResponseBody
    public void addDeviceTempalteVar(@RequestBody Map<String, String> varMap) throws BusinessException {
        System.out.println("进入了add-var方法");
        if(varMap.isEmpty()) return;

        String alarm = "";
        String template = varMap.get("ModelName");

        IoVariable var = buildVarMap2Xml(varMap);
        List<IoVariable> variableList = new ArrayList();
        variableList.add(var);

        deviceTemplateService.addIoDecieTemplateVars(template, alarm, variableList);
    }

    @RequestMapping(value = "/delvar")
    public void deleteDeviceTempalteVar(String template, List<IoBaseEntity> entityList) throws BusinessException {
        deviceTemplateService.deleteIoDeviceTemplateVar(template, entityList);
    }

    @RequestMapping(value = "/getvar")
    @ResponseBody
    public List<IoVariable> queryVarsFromTemplate(@RequestBody Map<String, String> temp_map){

        String template = temp_map.get("model_name");
        System.out.println("进入了请求所有变量的方法"+template);
        return deviceTemplateService.queryVarsByTemplate(template);

    }

    /*@RequestMapping(value = "/add-var-test")
    public String addVarTest(@RequestBody Map<String, String> temp_map) {
        *//*for (Map.Entry<String, String> entry : temp_map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        //创建变量
        List<IoVariable> varlist = new ArrayList();
        String model_name = temp_map.get("model_name");
        String var_name = temp_map.get("var_name");
        varlist.add(new IoVariable());
        addDeviceTempalteVar(model_name, "",  varlist);*//*
        return "menu";
    }

    @RequestMapping(value = "/add-test")
    public String addDeviceTempalteTest() {
        //创建模板
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("mytemplate");
        entities.add(entity);
        //addDeviceTempalte(entities);
        //创建变量
        List<IoBaseEntity> varlist = new ArrayList();
        varlist.add(new IoBaseEntity("pressure"));
        varlist.add(new IoBaseEntity("temperature"));
        varlist.add(new IoBaseEntity("water_volume"));
        addDeviceTempalteVar("mytemplate", varlist);*//*
        return "menu";
    }

    @RequestMapping(value = "/del-test")
    public String delDeviceTempalteTest() {
        List<IoBaseEntity> entities = new ArrayList();
        IoBaseEntity entity = new IoBaseEntity("mytemplate");
        entities.add(entity);
        deleteDeviceTempalte(entities);
        return "menu";
    }*/

    private IoVariable buildVarMap2Xml(Map<String, String> varMap) {

        String varName = varMap.get("VarName");
        String dataType = varMap.get("DataType");

        //TODO exception
        Document doc = DocumentHelper.createDocument();
        Element rootEle = doc.addElement("Values");
        Element subRoot = rootEle.addElement("Config");
        /*subRoot.addElement("ScanRate").setText(varMap.get("ScanRate"));
        subRoot.addElement("Area").setText(varMap.get("Area"));
        subRoot.addElement("Address").setText(varMap.get("Address"));
        subRoot.addElement("DataType").setText(varMap.get("DataType"));
        subRoot.addElement("HL").setText(varMap.get("HL"));
        subRoot.addElement("StringLen").setText(varMap.get("DataLength"));
        subRoot.addElement("ControlBit").setText(varMap.get("Controller"));
        subRoot.addElement("BitOffset").setText(varMap.get("BitOffset"));
        subRoot.addElement("RawMax").setText(varMap.get("raw_max"));
        subRoot.addElement("RawMin").setText(varMap.get("raw_min"));
        subRoot.addElement("RangeMax").setText(varMap.get("range_max"));
        subRoot.addElement("RangeMin").setText(varMap.get("range_min"));
        subRoot.addElement("DataChange").setText(varMap.get("data_change"));*/
        String propConf = doc.asXML();

        IoVariable var = new IoVariable();
        var.setName(varName);
        var.setDataType(dataType);
        var.setPropConf(propConf);

        return var;
    }
}
