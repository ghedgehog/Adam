package com.sunway.api;

import com.sunway.model.IoChannel;
import com.sunway.service.IoChannelService;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-channel")
public class IoChannelApi {

    @Autowired
    private IoChannelService ioChannelService;

    //查询新增通道
    @RequestMapping(value="/add")
    public List<IoChannel> queryAddedChannels(String driver){
        List<IoChannel> channels = ioChannelService.queryIoChannelsByMark(driver, Mark.INSERT);
        ioChannelService.setMark(driver, channels, Mark.DONE);
        return channels;
    }

    //查询删除通道
    @RequestMapping(value="/del")
    public List<IoChannel> queryDeletedChannels(String driver){
        List<IoChannel> channels = ioChannelService.queryIoChannelsByMark(driver, Mark.DELETE);
        ioChannelService.deleteIoChannels(driver, channels);
        return channels;
    }

    //GTEST
    @RequestMapping(value="/add-test")
    public List<IoChannel> queryAddedChannelTest(){
        return queryAddedChannels("ModbusTcpClient");
    }

    @RequestMapping(value="/del-test")
    public List<IoChannel> queryDeletedDriversTest(){
        return queryDeletedChannels("ModbusTcpClient");
    }
}
