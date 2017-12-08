package com.sunway.mapper;

import com.sunway.model.IoUaServer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IIoUaServerMapper {

    public void addIoUaServer(@Param("server") String uaServer);
}
