package com.victory.server.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData(String key, String value, Long expiredTime){
        //redisTemplate.opsForValue().set(key,value,expiredTime, TimeUnit.MICROSECONDS);
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key,value,expiredTime);
    }

    public String getData(String key){
        //return redisTemplate.opsForValue().get(key).toString();
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if(null == values.get(key)){
            return "";
        }else{
            return String.valueOf(values.get(key));
        }
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
