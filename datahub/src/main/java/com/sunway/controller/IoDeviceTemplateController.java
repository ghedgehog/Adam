package com.sunway.controller;

import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoVariable;
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
        String conf = "<Values><Config></Config></Values>";
        /*conf.concat("<ScanRate>").concat(varMap.get("scan_rate")).concat("</ScanRate>");
        conf.concat("<Area>").concat(varMap.get("area")).concat("</Area>");
        conf.concat("<Address>").concat(varMap.get("address")).concat("</Address>");
        conf.concat("<DataType>").concat(varMap.get("data_type")).concat("</DataType>");
        conf.concat("<HL>").concat(varMap.get("hl")).concat("</HL>");
        conf.concat("<StringLen>").concat(varMap.get("string_len")).concat("</StringLen>");
        conf.concat("<ControlBit>").concat(varMap.get("control_bit")).concat("</ControlBit>");
        conf.concat("<BitOffset>").concat(varMap.get("bit_offset")).concat("</BitOffset>");
        conf.concat("<RawMax>").concat(varMap.get("raw_max")).concat("</RawMax>");
        conf.concat("<RawMin>").concat(varMap.get("raw_min")).concat("</RawMin>");
        conf.concat("<RangeMax>").concat(varMap.get("range_max")).concat("</RangeMax>");
        conf.concat("<RangeMin>").concat(varMap.get("range_min")).concat("</RangeMin>");
        conf.concat("<DataChange>").concat(varMap.get("data_change")).concat("</DataChange>");*/
        //conf.concat("</Config></Values>");

        IoVariable var = new IoVariable();
        var.setName(varName);
        var.setDataType(dataType);
        var.setPropConf(conf);

        return var;
    }
}
