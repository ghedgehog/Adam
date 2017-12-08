package com.sunway.model;

/**
 * 驱动类型
 */
public class IoDriverType {

    private String name;
    private String description;
    private String communicationType;

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

    public String getCommunication_type() {
        return communicationType;
    }

    public void setCommunication_type(String communication_type) {
        this.communicationType = communicationType;
    }
}
