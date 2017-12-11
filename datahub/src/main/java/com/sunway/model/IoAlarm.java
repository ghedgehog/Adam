package com.sunway.model;

import java.sql.Date;

public class IoAlarm extends IoBaseEntity {
    private Date startTime;
    private Date endTime;
    private int severity;
    private int deviceId;
    private String sourceName;
    private String message;
    private String conditionName;
    private int active;

    public IoAlarm(String name,
                   Date startTime,
                   Date endTime,
                   int severity,
                   int deviceId,
                   String sourceName,
                   String message,
                   String conditionName,
                   int active) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
        this.severity = severity;
        this.deviceId = deviceId;
        this.sourceName = sourceName;
        this.message = message;
        this.conditionName = conditionName;
        this.active = active;
    }

    public IoAlarm(String name) {
        super(name);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
