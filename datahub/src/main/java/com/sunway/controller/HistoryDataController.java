package com.sunway.controller;

import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.model.IoVariable;
import com.sunway.service.HistoryDataService;
import com.sunway.service.IoDeviceService;
import com.sunway.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "his")
public class HistoryDataController {

    @Autowired
    HistoryDataService historyDataService;

    @Autowired
    IoDeviceService deviceService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> readDeviceHistoryDataList(@RequestBody  Map<String, String> temp_map) {

        String device = temp_map.get("device");
        String startTime = temp_map.get("startTime");
        String endTime = temp_map.get("endTime");
        System.out.println("hisData~~~~~~~~~~~~~~~~");
        return historyDataService.readDeviceHistoryDataList(device, startTime, endTime);
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public void writeDeviceHistoryData(String device, Map<String, Object> varMap) {
        historyDataService.writeDeviceHistoryData(device, varMap);
    }

    /*@RequestMapping(value = "/read-test", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> readHistoryDataByDevicesTest() throws ParseException {
        String device = "FORCE_HLS_SIM.channel.Blower";
        List<Map<String, Object>> hisDataList = readDeviceHistoryDataList(device,
                "2017-12-20 00:00:00",
                "2017-12-20 13:00:00");
        return hisDataList;
    }

    @RequestMapping(value = "/write-test")
    public String writeHistoryData() {
        String device = "ModbusTcpClient.channel1.myDevice";
        List<IoVariable> varList = deviceService.queryVarsFromDevice(device);
        Map<String, Object> varMap = new HashMap<String, Object>();
        for (IoVariable var : varList) {
            varMap.put(var.getName(), "varValueTest");
        }
        varMap.put("time", new Date());
        varMap.put("device_name", device);
        //GTODO location insert error 需类型转换
        //varMap.put("location", "POINT(22.5 60.87)");
        writeDeviceHistoryData(device, varMap);
        return "menu";
    }*/
}
