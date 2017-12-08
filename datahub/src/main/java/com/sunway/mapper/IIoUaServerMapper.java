package com.sunway.mapper;

import com.sunway.model.IoUaServer;

public interface IIoUaServerMapper {

    public boolean setUaServer(String name);

    public boolean setUaServer(IoUaServer uaServer);
}
