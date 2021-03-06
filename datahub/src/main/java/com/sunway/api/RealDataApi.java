package com.sunway.api;

import com.sunway.service.HistoryDataService;
import com.sunway.service.RealDataService;
import com.sunway.utils.JsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="real")
public class RealDataApi {

    @Autowired
    RealDataService realDataService;

    @Autowired
    HistoryDataService historyDataService;

    JsonTool dataTool = new JsonTool();

    @RequestMapping(value="/write", method = RequestMethod.POST)
    public void writeRealData(String name, String value){
        realDataService.addRealDataByVar(name, value);
    }
}
