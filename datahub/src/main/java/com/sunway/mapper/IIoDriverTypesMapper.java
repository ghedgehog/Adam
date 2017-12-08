package com.sunway.mapper;

import com.sunway.model.IoDriverType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface IIoDriverTypesMapper {

    public List<IoDriverType> queryDriverTypes();
}
