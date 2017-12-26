package com.sunway.api;

import com.sunway.exception.BusinessException;
import com.sunway.service.IoConfigChangeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api-change")
public class IoConfigChangeApi {

    private IoConfigChangeService configChangeService;

    //查询更新表最大版本号
    @RequestMapping(value="/get-value", method = RequestMethod.GET)
    public int getUpdateMaxValue() throws BusinessException {
        return configChangeService.getUpdateMaxValue();
    }

    //查询更新表中大于version的table_name, 对应驱动、设备表名
    @RequestMapping(value="/get-list", method = RequestMethod.GET)
    public List<String> getUpdateTablesByVersion(int version) throws BusinessException {
        return configChangeService.getUpdateTablesByVersion(version);
    }
}
