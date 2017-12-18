package com.sunway.model;

public class IoAlarmConfig /*extends IoBaseEntity*/ {

    private String name;
    private String conf;

    /*public IoAlarmConfig(String name) {
        super(name);
    }
    */

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
}
