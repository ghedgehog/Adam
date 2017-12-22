package com.sunway.service;

import com.sunway.exception.BusinessException;
import com.sunway.mapper.IIoBaseMapper;
import com.sunway.mapper.IIoTableName;
import com.sunway.model.IoUaServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ioUaServerService")
public class IoUaServerService {

    private String tableName = IIoTableName.IoUaServers;

    @Autowired
    private IIoBaseMapper<IoUaServer> baseMapper;

    public void addIoUaServer(String uaServer) throws BusinessException {
        try{
            List<IoUaServer> list = new ArrayList();
            list.add( new IoUaServer(uaServer));
            baseMapper.addSysBaseList(tableName, list);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
