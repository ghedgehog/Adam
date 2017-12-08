package com.sunway.mapper;

import com.sunway.model.IoDriver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface IIoDriverMapper {

    public void addIoDrivers(@Param("server") String uaServer,
                             @Param("list") List<IoDriver> drivers);

    public void deleteIoDrivers(@Param("server") String uaServer,
                                @Param("list") List<IoDriver> drivers);

    public List<IoDriver> queryIoDrivers(@Param("server") String uaServer,
                                         @Param("mark") int mark);

    public void setMark(@Param("list") List<IoDriver> drivers,
                        @Param("mark") int mark);

    //TODO
    public void updateIoDrivers();
}
