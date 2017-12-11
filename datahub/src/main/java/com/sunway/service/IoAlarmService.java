package com.sunway.service;

import com.sunway.mapper.IIoExtendMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoAlarm;
import com.sunway.model.IoDriverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;

@Service("ioAlarmService")
public class IoAlarmService {

    @Autowired
    private IIoExtendMapper<IoAlarm> extendMapper;

    private String tableName = IIoTableName.IoAlarm;

    public void addAlarm(List<IoAlarm> alarms){
        extendMapper.addAlarms(tableName, alarms);
    }

    public List<IoAlarm> queryAlarm(Date startTime, Date endTime){
        return extendMapper.queryAlarms(tableName, startTime, endTime);
    }
}
