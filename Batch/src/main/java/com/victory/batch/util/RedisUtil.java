package com.victory.batch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData(String key, String value, Long expiredTime){
        ValueOperations<String, Object> val = redisTemplate.opsForValue();
        val.set(key,value);
    }

    public String getData(String key){
        ValueOperations<String, Object> val = redisTemplate.opsForValue();
        if(null == val.get(key)){
            return "";
        }else{
            return String.valueOf(val.get(key));
        }
    }

    /**
     * For Token Pair Getter and Setter
     **/
    public void setPrivateKeyData(String key, String value, Long exp){
        ValueOperations<String, Object> val = redisTemplate.opsForValue();
        val.set(key.concat("Private"),value);
    }
    public void setPublicKeyData(String key, String value, Long exp){
        ValueOperations<String, Object> val = redisTemplate.opsForValue();
        val.set(key.concat("Public"),value);
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
