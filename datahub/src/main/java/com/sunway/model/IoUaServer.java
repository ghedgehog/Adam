package com.sunway.model;

public class IoUaServer extends IoBaseEntity{

    private String ip;
    private String port;
    private boolean status;

    public IoUaServer(String name) {
        super(name);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
