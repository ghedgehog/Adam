package com.sunway.model;

public class IoDriverTypes {

    private String name;
    private String description;
    private String category;
    private String communicationtype;
    //private String extend JSONB;
    private int mark; // INT -- 1-insert 2-update 3-delete 4-done(采集服务处理完毕)

    public IoDriverTypes(String name, String description, String category, String communicationtype, int mark) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.communicationtype = communicationtype;
        this.mark = mark;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCommunicationtype() {
        return communicationtype;
    }

    public void setCommunicationtype(String communicationtype) {
        this.communicationtype = communicationtype;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
