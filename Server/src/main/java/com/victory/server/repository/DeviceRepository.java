package com.victory.server.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash(value = "device_repository")
public class DeviceRepository {
    private String appId;
    @Id
    private String usrId;
    @Indexed
    private String mobileNo;
    private char   osType;
    private String osVersion;
    private String tokenPrivate;
    private String tokenPublic;
    private char   useAt;
    private String regDt;
    private String regId;
    private String updDt;
    private String updId;

    @TimeToLive
    private long timeToLive;

    public DeviceRepository update(String tokenPrivate, String tokenPublic, long timeToLive){
        this.tokenPrivate = tokenPrivate;
        this.tokenPublic = tokenPublic;
        return this;
    }
}