package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
import com.sunway.service.IoDeviceTemplateService;
import com.sunway.service.RealDataService;
import com.sunway.utils.DataTool;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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

        IoBaseEntity entity = new IoBaseEntity(temp_map.get("model-name"));

        if(!createTime.isEmpty())
            entity.setCreateTime(DataTool.getDateAndTimeFromString(createTime));
        if(!modifyTime.isEmpty())
            entity.setCreateTime(DataTool.getDateAndTimeFromString(modifyTime));

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

    @RequestMapping(value = "/add-var")
    @ResponseBody
    public void addDeviceTempalteVar(@RequestBody Map<String, String> varMap) {
        if(varMap.isEmpty()) return;

        String alarm = "";
        String template = varMap.get("model_name");

        IoVariable var = buildVarMap2Xml(varMap);
        List<IoVariable> variableList = new ArrayList();
        variableList.add(var);

        deviceTemplateService.addIoDecieTemplateVars(template, alarm, variableList);
    }

    @RequestMapping(value = "/delvar")
    public void deleteDeviceTempalteVar(String template, List<IoBaseEntity> entityList) {
        deviceTemplateService.deleteIoDeviceTemplateVar(template, entityList);
    }

    @RequestMapping(value = "/add-var-test")
    public String addVarTest(@RequestBody Map<String, String> temp_map) {
        /*for (Map.Entry<String, String> entry : temp_map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        //创建变量
        List<IoVariable> varlist = new ArrayList();
        String model_name = temp_map.get("model_name");
        String var_name = temp_map.get("var_name");
        varlist.add(new IoVariable());
        addDeviceTempalteVar(model_name, "",  varlist);*/
        return "menu";
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

    private IoVariable buildVarMap2Xml(Map<String, String> varMap) {

        String varName = varMap.get("var_name");
        String dataType = varMap.get("data_type");

        //TODO exception
        Document doc = DocumentHelper.createDocument();
        Element rootEle = doc.addElement("Values");
        Element subRoot = rootEle.addElement("Config");
        /*subRoot.addElement("ScanRate").setText(varMap.get("scan_rate"));
        subRoot.addElement("Area").setText(varMap.get("area"));
        subRoot.addElement("Address").setText(varMap.get("address"));
        subRoot.addElement("DataType").setText(varMap.get("data_type"));
        subRoot.addElement("HL").setText(varMap.get("hl"));
        subRoot.addElement("StringLen").setText(varMap.get("string_len"));
        subRoot.addElement("ControlBit").setText(varMap.get("control_bit"));
        subRoot.addElement("BitOffset").setText(varMap.get("bit_offset"));
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
