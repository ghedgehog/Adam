package com.sunway.model;

import com.sunway.utils.Utils;

/**
 * 驱动类型
 */
public class IoDriverType {

    private String name;
    private String description;
    private String communicationType;
    private Utils.Mark mark;

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

    public Utils.Mark getMark() {
        return mark;
    }

    public void setMark(Utils.Mark mark) {
        this.mark = mark;
    }

    public String getCommunication_type() {
        return communicationType;
    }

    public void setCommunication_type(String communication_type) {
        this.communicationType = communicationType;
    }
}
