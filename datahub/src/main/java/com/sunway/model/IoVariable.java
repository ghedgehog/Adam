package com.sunway.model;

public class IoVariable {
    private String name;
    private String description;
    private String dataType; // -- 数据类型，整形表示，具体TD
    private String propConf; //  XML, -- tag采集配置信息
    /*private boolean propDataChange; //   -- 量程变换
    private double propRangeMin; //  -- 量程下限
    private double propRangeMax; //  -- 量程上限
    private double propRawMin; //  -- 裸数据下限
    private double propRawMax; // -- 裸数据上限*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPropConf() {
        return propConf;
    }

    public void setPropConf(String propConf) {
        this.propConf = propConf;
    }

}
