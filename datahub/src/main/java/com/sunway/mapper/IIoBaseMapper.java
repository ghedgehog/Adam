package com.sunway.mapper;

import com.sunway.model.IoAlarmConfig;
import com.sunway.model.IoBaseEntity;
import com.sunway.model.IoDevice;
import com.sunway.model.IoVariable;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface IIoBaseMapper<T extends IoBaseEntity> {

    public int isTableExists(@Param("table") String tableName);

    public void addSysBaseList(@Param("table") String tableName,
                               @Param("list") List<T> baseList);

    public void deleteSysBaseList(@Param("table") String tableName,
                                  @Param("list") List<T> baseList);

    public List<String> querySysBaseList(@Param("table") String tableName);

    public int addBaseList(@Param("table") String tableName,
                            @Param("ptable") String ptableName,
                            @Param("parent") String parent,
                            @Param("list") List<T> baseList);

    public void deleteBaseList(@Param("table") String tableName,
                               @Param("ptable") String ptableName,
                               @Param("parent") String parent,
                               @Param("list") List<T> baseList);

    public List<T> queryIoBaseList(@Param("table") String tableName,
                                   @Param("ptable") String ptableName,
                                   @Param("parent") String parent,
                                   @Param("mark") int mark);

    public List<T> queryAllIoBase(@Param("table") String tableName);

    public void setMark(@Param("table") String tableName,
                        @Param("ptable") String ptableName,
                        @Param("list") List<T> baseList,
                        @Param("parent") String parent,
                        @Param("mark") int mark);

    public void setSysMark(@Param("table") String tableName,
                           @Param("list") List<T> baseList,
                           @Param("mark") int mark);

    public void addIoDevices(@Param("table") String tableName,
                             @Param("ptable") String ptable,
                             @Param("tempTable") String tempTable,
                             @Param("parent") String parent,
                             @Param("tempName") String tempName,
                             @Param("list") List<IoDevice> baseList);

    public List<IoDevice> queryIoDevices(@Param("table") String tableName,
                                         @Param("ptable") String ptableName,
                                         @Param("parent") String parent,
                                         @Param("mark") int mark);

    public void addIoVariables(@Param("varTable") String varTable,
                               @Param("tempTable") String tempTable,
                               @Param("alarmTable") String alarmTable,
                               @Param("template") String template,
                               @Param("alarm") String alarm,
                               @Param("list") List<IoVariable> variableList);

    public List<IoVariable> queryVarsFromDevice(@Param("varTable") String varTable,
                                                @Param("devTable") String devTable,
                                                @Param("devName") String devName);

    public List<IoVariable> queryVarsFromTemplate(@Param("varTable") String varTable,
                                                @Param("tempTable") String tempTable,
                                                @Param("template") String template);

    public List<IoAlarmConfig> queryAlarmConfigByVar(@Param("alarmTable") String alarmTable,
                                                     @Param("varTable") String varTable,
                                                     @Param("varName") String var);

    public List<IoAlarmConfig> queryAlarmConfig(@Param("alarmTable") String alarmTable,
                                                @Param("mark") int mark);

    //2017-12-26 add
    public int queryUpdateMaxValue(@Param("updateTable") String updateTable);

    public List<String>  queryUpdateTablesByVersion(@Param("updateTable") String updateTable,
                                                    @Param("version") int version);


}
