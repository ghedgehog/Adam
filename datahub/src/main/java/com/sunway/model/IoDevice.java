package com.sunway.model;

public class IoDevice {
    private String name;
    private String description;
    private String propConf; //  XML, -- tag采集配置信息

    /*public IoDevice(String name) {
        this.name = name;
        this.description = "";
    }*/

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

    public String getPropConf() {
        return propConf;
    }

    public void setPropConf(String propConf) {
        this.propConf = propConf;
    }
}
