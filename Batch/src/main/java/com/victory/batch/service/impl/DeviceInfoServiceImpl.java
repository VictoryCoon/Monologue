package com.victory.batch.service.impl;

import com.victory.batch.mapper.DeviceInfoMapper;
import com.victory.batch.repository.DeviceRepository;
import com.victory.batch.service.DeviceInfoService;
import com.victory.batch.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean deviceInfoCaching() {
        SqlSession session = sqlSessionFactory.openSession(false);

        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        DeviceInfoMapper mapper = session.getMapper(DeviceInfoMapper.class);

        List<DeviceRepository> list = mapper.selectDeviceInfo();
        if(list.isEmpty()){
            return false;
        }else{
            for(DeviceRepository deviceInfo : list){
                boolean comparePrivateVal;
                boolean comparePublicVal;
                //PrivateKey Compare
                if(redisUtil.getPrivateKeyData(deviceInfo.getUsrId()).equals(deviceInfo.getTokenPrivate())){
                    comparePrivateVal = true;
                }else{
                    comparePrivateVal = false;
                }

                //PublicKey Compare
                if(redisUtil.getPublicKeyData(deviceInfo.getUsrId()).equals(deviceInfo.getTokenPublic())){
                    comparePublicVal = true;
                }else{
                    comparePublicVal = false;
                }
                if(!(comparePrivateVal&&comparePublicVal)){
                    redisUtil.setPrivateKeyData(deviceInfo.getUsrId(),deviceInfo.getTokenPrivate(),0L);
                    redisUtil.setPublicKeyData(deviceInfo.getUsrId(),deviceInfo.getTokenPublic(),0L);
                    log.info("Key Pair is updated {} : {}",deviceInfo.getTokenPrivate(),deviceInfo.getTokenPublic());
                }
            }
            return true;
        }
    }
}