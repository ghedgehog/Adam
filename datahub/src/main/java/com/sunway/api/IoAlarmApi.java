package com.sunway.api;

import com.sunway.exception.BusinessException;
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
    public void addAlarms(List<IoAlarm> alarmList) throws BusinessException {
        ioAlarmService.addAlarm(alarmList);
    }

}
