package com.sunway.service;

import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoVariable;
import com.sunway.utils.Data;
import com.sunway.utils.HisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("historyDataService")
public class HistoryDataService {

    @Autowired
    private IIoHisDataMapper hisDataMapper;

    @Autowired
    private IoDeviceService deviceService;

    public void initial(){
        deviceService.init();
    }

    public String queryTemplateName(String device) {
        return hisDataMapper.queryTemplateName(IIoTableName.IoDeviceTemplate,
                IIoTableName.IoDevice,
                device);
    }

    public String getHistoryTableName(String device) {
        String template = queryTemplateName(device);
        if(template == null) return  null;
        return HisData.hisTablePrefix + template;
    }

    public void crateHisTableByTemplate(String tempTable, List<String> varName) {
        hisDataMapper.crateHisTableByTemplate(tempTable, varName);
    }

    public List<Map<String, Object>> readDeviceHistoryDataList(String device, String startTime, String endTime) {

        if(device == null || device.isEmpty()) return null;

        List<IoVariable> varList = deviceService.queryVarsFromDevice(device);
        List<String> list = new ArrayList();

        for (IoVariable variable : varList) {
            list.add(variable.getName());
        }

        return hisDataMapper.readDeviceHistoryData(
                getHistoryTableName(device).toLowerCase(),
                device,
                list,
                startTime, endTime);
    }

    public List<Object> readHistoryDataByDeviceVars(String var, Date startTime, Date endTime) {
        return null;
    }

    public void writeDeviceHistoryData(String device, Map<String, Object> params) {
        if(params==null || params.isEmpty()) return;

        String hisTable = getHistoryTableName(device);

        if(hisTable != null){
            hisDataMapper.writeDeviceHistoryData(hisTable, params);
        }
    }
}
