package com.sunway.service;

import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("historyDataService")
public class HistoryDataService {

    @Autowired
    private IIoHisDataMapper hisDataMapper;

    public void crateHisTableByTemplate(String tempTable, List<String> varName){
        hisDataMapper.crateHisTableByTemplate(tempTable, varName);
    }

    public HashMap<Long, Map<String, String>> readHistoryDataByDevices(String var, Date startTime, Date endTime){
        return null;
    }

    public Map<Date, String> readHistoryDataByDeviceVars(String var, Date startTime, Date endTime){
        return null;
    }

    /*
    * INSERT INTO his_mytemplate(time, device_name, location, pressure, temperature, water_volume) VALUES(
	* '2017-12-12 1:00:00', 'device', 'POINT(31.5 60.87)', 12, 22, 33);
	*/
    public void writeHistoryDataByDevice(){

    }
}
