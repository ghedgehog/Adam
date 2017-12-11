package com.sunway.service;

import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoChannel;
import com.sunway.utils.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ioChannelService")
public class IoChannelService {

    @Autowired
    private IIoBaseMapper<IoChannel> baseMapper;

    private String tableName = IIoTableName.IoChannel;
    private String ptableName = IIoTableName.IoDriver;

    public void addIoChannels(String driver, List<IoChannel> channels){
        baseMapper.addBaseList(tableName, ptableName, driver, channels);
    }

    public void deleteIoChannels(String driver, List<IoChannel> channels){
        //baseMapper.setMark(tableName, ptableName, channels, driver, Mark.DELETE);
        baseMapper.deleteBaseList(tableName, ptableName, driver, channels);
    }

    public List<IoChannel> queryIoChannels(String driver, int mark){
        return baseMapper.queryIoBaseList(tableName, ptableName, driver, mark);
    }

    public void setMark(String driver, List<IoChannel> channels, int mark){
        baseMapper.setMark(tableName, ptableName, channels, driver, mark);
    }
}
