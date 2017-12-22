package com.sunway.controller;

import com.sunway.exception.BusinessException;
import com.sunway.model.IoAlarm;
import com.sunway.service.IoAlarmService;
import com.sunway.service.RealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="alarm")
public class IoAlarmController {

    @Autowired
    private IoAlarmService alarmService;

    @Autowired
    private RealDataService dataService;

    @RequestMapping(value="/add")
    public void addAlarms(List<IoAlarm> alarmList) throws BusinessException {
        alarmService.addAlarm(alarmList);
        //TRUE TODO
        dataService.NoticeAlarmAdded();
    }

    @RequestMapping(value="/query")
    public void queryAlarms(){
        //List<IoAlarm> alarms = alarmService.queryAlarm("2017-12-11 12:00:00", "2017-12-12 12:00:00");
    }
}
