package com.sunway.controller;

import com.sunway.model.IoChannel;
import com.sunway.service.IoChannelService;
import com.sunway.service.RealDataService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="channel")
public class IoChannelController {

    @Autowired
    private IoChannelService ioChannelService;

    @Autowired
    private RealDataService dataService;

    @RequestMapping(value="/add")
    public void addIoChannels(String driver, List<IoChannel> channels){
        ioChannelService.addIoChannels(driver, channels);
        //TRUE TODO
        dataService.NoticeChannelAdded();
    }

    @RequestMapping(value="/del")
    public void deleteIoChannels(String driver, List<IoChannel> channels){
        //ioChannelService.deleteIoChannels(uaServer, channels);
        ioChannelService.setMark(driver, channels, Mark.DELETE);
        //TRUE TODO
        dataService.NoticeChannelDeleted();
    }

    /*//GTEST
    @RequestMapping(value="/add-test")
    public String addIoChannelsTest(){
        List<IoChannel> channels = new ArrayList();
        channels.add(new IoChannel("ModbusTcpClient.channel4"));
        channels.add(new IoChannel("ModbusTcpClient.channel5"));
        addIoChannels("ModbusTcpClient", channels);
        return "menu";
    }

    //GTEST
    @RequestMapping(value="/del-test")
    public String deleteIoDriversTest(){
        List<IoChannel> channels = new ArrayList();
        channels.add(new IoChannel("ModbusTcpClient.channel4"));
        channels.add(new IoChannel("ModbusTcpClient.channel5"));
        deleteIoChannels("ModbusTcpClient", channels);
        return "menu";
    }*/
}
