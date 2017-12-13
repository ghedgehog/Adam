package com.sunway.utils;

public class Data {
    public long time;
    public int type;
    public String value;
    public int quality;

    public Data(long time, int type, String value, int quality) {
        this.time = time;
        this.type = type;
        this.value = value;
        this.quality = quality;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
