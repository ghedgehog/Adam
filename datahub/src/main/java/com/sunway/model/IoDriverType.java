package com.sunway.model;

/**
 * 驱动类型
 */
public class IoDriverType extends IoBaseEntity{

    private String communicationType;

    public IoDriverType(String name, String communicationType) {
        super(name);
        this.communicationType = communicationType;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }
}
