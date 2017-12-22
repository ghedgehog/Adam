package com.sunway.service;

import com.sunway.exception.BusinessException;
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

    public int addIoChannels(String driver, List<IoChannel> channels) throws BusinessException {
        try{
            return baseMapper.addBaseList(tableName, ptableName, driver, channels);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void deleteIoChannels(String driver, List<IoChannel> channels) throws BusinessException {
        try{
            //baseMapper.setMark(tableName, ptableName, channels, driver, Mark.DELETE);
            baseMapper.deleteBaseList(tableName, ptableName, driver, channels);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }

    }

    public List<IoChannel> queryIoChannelsByMark(String driver, int mark){
        return baseMapper.queryIoBaseList(tableName, ptableName, driver, mark);
    }

    public void setMark(String driver, List<IoChannel> channels, int mark) throws BusinessException {
        try{
            baseMapper.setMark(tableName, ptableName, channels, driver, mark);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }

    }
}
