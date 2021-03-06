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
        if(varMap.isEmpty()) return;

        String alarm = "";
        String template = varMap.get("ModelName");
        /*for (Map.Entry<String, String> entry : varMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }*/
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

    private String getVarValueString(String varStr){
        if(varStr == null || varStr.isEmpty())
            return "";
        else
            return varStr;
    }

    private IoVariable buildVarMap2Xml(Map<String, String> varMap) {

        String varName = varMap.get("VarName");
        String dataType = varMap.get("DataType");

        //TODO exception
        Document doc = DocumentHelper.createDocument();
        Element rootEle = doc.addElement("Values");
        Element subRoot = rootEle.addElement("Config");
        Element third = subRoot.addElement("Values");
        third.addElement("ScanRate").setText(getVarValueString(varMap.get("ScanRate")));
        third.addElement("Area").setText( getVarValueString(varMap.get("Area")));
        third.addElement("Address").setText( getVarValueString( varMap.get("Address")));
        third.addElement("DataType").setText(varMap.get("DataType"));
        third.addElement("HL").setText( getVarValueString( varMap.get("HL")));
        third.addElement("StringLen").setText( getVarValueString(varMap.get("StringLen")));
        third.addElement("ControlBit").setText( getVarValueString(varMap.get("ControlBit")));
        third.addElement("BitOffset").setText( getVarValueString(varMap.get("BitOffset")));

        rootEle.addElement("RawMax").setText( getVarValueString(varMap.get("RawMax")));
        rootEle.addElement("RawMin").setText( getVarValueString(varMap.get("RawMin")));
        rootEle.addElement("RangeMax").setText( getVarValueString(varMap.get("RangeMax")));
        rootEle.addElement("RangeMin").setText( getVarValueString(varMap.get("RangeMin")));
        rootEle.addElement("DataChange").setText( getVarValueString(varMap.get("DataChange")));
        String propConf = doc.asXML();

        IoVariable var = new IoVariable();
        var.setName(varName);
        var.setDataType(dataType);
        var.setPropConf(propConf);

        return var;
    }
}
