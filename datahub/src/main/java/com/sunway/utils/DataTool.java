package com.sunway.utils;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTool {

    private String data;

    private Gson gson = new Gson();

    private static SimpleDateFormat calendar = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat calendarAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date getDateFromString(String strDate){
        try {
            return  calendar.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateAndTimeFromString(String strDate){
        try {
            return  calendarAndTime.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString(Data data){
        return  gson.toJson(data);
    }

    public Data fromString(String jsonString){
        return  gson.fromJson(jsonString, Data.class);
    }
}
