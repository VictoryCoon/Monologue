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

    public String getPrivateKeyData(String key){
        ValueOperations<String, Object> val = redisTemplate.opsForValue();
        String concatenation = key.concat("Private");
        if(null == val.get(concatenation)){
            return "";
        }else{
            return String.valueOf(val.get(concatenation));
        }
    }

    public String getPublicKeyData(String key){
        ValueOperations<String, Object> val = redisTemplate.opsForValue();
        String concatenation = key.concat("Public");
        if(null == val.get(concatenation)){
            return "";
        }else{
            return String.valueOf(val.get(concatenation));
        }
    }
}
