package com.sunway.mapper;

import com.sunway.utils.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Repository
public interface IIoHisDataMapper {

    public void addHistoryData(String name, Data data);

    public List<Data> queryHistoryData(long startTime, long endTime);

    public void crateHisTableByTemplate(@Param("table") String tempTable,
                                        @Param("list") List<String> varName);

    public void writeHistoryDataByDevice(@Param("table")String tempTable,
                                         @Param("param")HashMap<String, Object> params);
}
