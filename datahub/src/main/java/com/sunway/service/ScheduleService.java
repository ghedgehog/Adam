package com.sunway.service;

import com.sunway.model.IoBaseEntity;
import com.sunway.utils.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sunway.utils.Notice.DEVICELIST;

@Component
public class ScheduleService {

    public final static long SCHE_SECOND =  1 * 1000;

    private boolean isInitialize = false;

    @Autowired
    private RealDataService realDataService;

    @Autowired
    private HistoryDataService hisDataService;

    //@Scheduled(initialDelay = SCHE_SECOND*2, fixedDelay=SCHE_SECOND*1)
    private void transferReal2His(){

        if(!isInitialize){ hisDataService.initial();  isInitialize =true;}
        List<Object> deviceList = realDataService.getAllDevices();

        for (Object device : deviceList){
            ArrayList temp = (ArrayList)device;
            for(Object obj : temp){
                Map<String, Object> varMap = realDataService.queryRealDataByDevice((String)obj);
                if(varMap!= null) varMap.put("device_name", (String)obj);
                //varMap.put("location", "POINT(116.39 39.9)");
                hisDataService.writeDeviceHistoryData((String)obj, varMap);
            }
        }
    }
}
