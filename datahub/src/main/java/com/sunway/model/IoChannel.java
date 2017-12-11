package com.sunway.model;

public class IoChannel extends IoBaseEntity{

    private boolean status;

    public IoChannel(String name) {
        super(name);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
