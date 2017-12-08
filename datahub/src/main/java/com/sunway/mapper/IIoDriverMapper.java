package com.sunway.mapper;

import com.sunway.model.IoDriver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface IIoDriverMapper {

    public void addIoDrivers(@Param("uaServer") String uaServer, @Param("list")List<IoDriver> drivers);

    public List<IoDriver> queryIoDrivers(@Param("uaServer") String uaServer);
}
