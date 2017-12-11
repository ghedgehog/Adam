package com.sunway.mapper;

import com.sunway.model.IoAlarm;
import com.sunway.model.IoBaseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional
@Repository
public interface IIoExtendMapper<T extends IoBaseEntity> {

    public List<T> queryDriverType(@Param("table") String tableName);

    public void addAlarms(@Param("table") String tableName,
                          @Param("list")List<IoAlarm> alarms);

    public List<IoAlarm> queryAlarms(@Param("table") String tableName,
                                     @Param("start_time")Date startTime,
                                     @Param("end_time")Date endTime);

    public void addDevices(@Param("table") String tableName,
                           @Param("ptable") String ptableName,
                           @Param("tempTable") String tempTable,
                           @Param("parent") String parent,
                           @Param("tempName") String tempName,
                           @Param("list") List<T> baseList);

    public List<T> queryDevices(@Param("table") String tableName,
                                   @Param("ptable") String ptableName,
                                   @Param("parent") String parent,
                                   @Param("mark") int mark);
}
