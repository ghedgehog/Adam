package com.sunway.mapper;

import com.sunway.utils.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public interface IIoHisDataMapper {

    public String queryTemplateName(@Param("tempTable") String tempTable,
                                    @Param("devTable") String devTable,
                                    @Param("devName") String deivce);

    public void crateHisTableByTemplate(@Param("table") String tempTable,
                                        @Param("list") List<String> varName);

    public void writeDeviceHistoryData(@Param("table")String hisTable,
                                         @Param("params")Map<String, Object> params);

    public List<Map<String, Object>> readDeviceHistoryData(
                                        @Param("table") String hisTable,
                                        @Param("device") String device,
                                        @Param("list") List<String> title,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime);

    public List<Object> readVarHistoryData(String var, Date startTime, Date endTime);
}
