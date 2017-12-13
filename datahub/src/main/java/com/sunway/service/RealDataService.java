package com.sunway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("realDataService")
public class RealDataService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //set("key","value")
    public void set(String k, String v){
        redisTemplate.opsForValue().set(k, v);
    }

    //set("key","value",10, TimeUnit.SECONDS);
    public void set(String k, String v, long timeout,TimeUnit unit){
        redisTemplate.opsForValue().set(k,v,timeout, unit);
    }

    public String get(String k){
        return redisTemplate.opsForValue().get(k).toString();
    }

}
