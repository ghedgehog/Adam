package com.sunway.service;

import com.sunway.model.IoVariable;
import com.sunway.utils.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("realDataService")
public class RealDataService {

     @Autowired
    private RedisTemplate redisTemplate;

    public void addRealDataByVar(String name, String value){
        set(name, value);
    }

    public Object queryRealDataByVar(String varName){
        return get(varName);
    }

    public Map<String, Object> queryRealDataByDevice(String deivceName){

        String val = "";
        Map<String, Object> valMap = new HashMap<String, Object>();
        List<Object> variables = getVariables(deivceName);

        for(Object var: variables){
            String valName = deivceName + "." + var;
            Object value = get(valName);
            //简单校验，设备下所有变量无数据不插入数据
            if(value != null) val += value.toString();
            //存入短名
            valMap.put((String)var , value);
        }

        if(val != "" ){
            valMap.put("time", new Date());
            return valMap;
        }
        return null;
    }

    public void updateAllDevices(List<String> devices){
        redisTemplate.delete(Notice.DEVICELIST);
        listPush(Notice.DEVICELIST, devices);
    }

    public List<Object> getAllDevices(){
        return listRange(Notice.DEVICELIST, 0, -1);
    }

    public void updateVariables(String dev, List<String> varList){
        redisTemplate.delete(dev+Notice.DEVICESUFFIX);
        for(String var : varList){
            listPush(dev + Notice.DEVICESUFFIX, var);
        }
    }

    public List<Object> getVariables(String device){
        return listRange(device + Notice.DEVICESUFFIX, 0, -1);
    }


    private boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    private void listPush(String k,Object v){
        ListOperations<String, Object> listOper = redisTemplate.opsForList();
        listOper.rightPush(k,v);
    }

    private List<Object> listRange(String k, long l, long l1){
        ListOperations<String, Object> listOper = redisTemplate.opsForList();
        return listOper.range(k,l,l1);
    }


    public void NoticeDeviceAdded(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.DEVICEADD);
    }

    public void NoticeDeviceDeleted(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.DEVICEDEL);
    }

    public void NoticeDeviceUpdated(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.DEVICEUPDATE);
    }

    public void NoticeDriverAdded(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.DRIVERADD);
    }

    public void NoticeDriverDeleted(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.DRIVERDEL);
    }

    public void NoticeDriverUpdated(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.DRIVERUPDATE);
    }

    public void NoticeChannelAdded(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.CHANNELADD);
    }

    public void NoticeChannelDeleted(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.CHANNELDEL);
    }

    public void NoticeChannelUpdated(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.CHANNELUPDATE);
    }

    public void NoticeVariableAdded(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.VARIABLEADD);
    }

    public void NoticeVariableDeleted(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.VARIABLEDEL);
    }

    public void NoticeVariableUpdated(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.VARIABLEUPDATE);
    }

    public void NoticeAlarmAdded(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.ALARMADD);
    }

    public void NoticeAlarmDeleted(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.ALARMDEL);
    }

    public void NoticeAlarmUpdated(){
        redisTemplate.convertAndSend(Notice.TOPIC , Notice.ALARMUPDATE);
    }
}
