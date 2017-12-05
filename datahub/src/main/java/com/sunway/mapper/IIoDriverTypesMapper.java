package com.sunway.mapper;

import com.sunway.model.IoDriverTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IIoDriverTypesMapper {

    IoDriverTypes queryDriverTypes();
}
