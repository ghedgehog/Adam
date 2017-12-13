package com.sunway.utils;

import com.google.gson.Gson;

public class DataTool {

    private String data;

    private Gson gson = new Gson();

    public String toString(Data data){
        return  gson.toJson(data);
    }

    public Data fromString(String jsonString){
        return  gson.fromJson(jsonString, Data.class);
    }
}
