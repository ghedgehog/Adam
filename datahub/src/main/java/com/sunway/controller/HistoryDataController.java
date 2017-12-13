package com.sunway.controller;

import com.sunway.mapper.IIoHisDataMapper;
import com.sunway.service.HistoryDataService;
import com.sunway.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="his")
public class HistoryDataController {

    @Autowired
    HistoryDataService historyDataService;

    public String readHistoryDataByDevices(Date startTime, Date endTime){
        return null;
    }

    public String readHistoryDataByDeviceVars(Date startTime, Date endTime){
        return null;
    }
}
