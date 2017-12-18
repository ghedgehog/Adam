package com.sunway.model;

public class IoAlarmConfig /*extends IoBaseEntity*/ {

    private String name;
    private String conf;
    private String type;

    /*public IoAlarmConfig(String name) {
        super(name);
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
