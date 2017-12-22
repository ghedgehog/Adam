package com.sunway.api;

import com.sunway.model.IoAlarm;
import com.sunway.service.IoAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value="api-alarm")
public class IoAlarmApi {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IoAlarmService ioAlarmService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public void addAlarms(List<IoAlarm> alarmList){
        ioAlarmService.addAlarm(alarmList);
    }

    //TODO
    @RequestMapping(value="/add-test", method = RequestMethod.POST)
    public void addAlarmsTest(){
        List<IoAlarm> alarms = new ArrayList();
        alarms.add(new IoAlarm("",
                (java.sql.Date.valueOf("2017-12-12")),
                (java.sql.Date.valueOf("2017-12-12")),
                1, 1, "device1",
                "", "", 1));
        addAlarms(alarms);
    }
}
