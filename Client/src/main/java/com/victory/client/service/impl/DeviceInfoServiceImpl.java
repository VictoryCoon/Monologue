package com.victory.client.service.impl;

import com.victory.client.mapper.DeviceInfoMapper;
import com.victory.client.repository.DeviceRepository;
import com.victory.client.service.DeviceInfoService;
//import com.victory.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    //@Autowired
    //RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean deviceInfoCaching() {
        log.info("Get In : deviceInfoCaching");
        SqlSession session = sqlSessionFactory.openSession(false);
        //RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //RedisUtil redisUtil = new RedisUtil(redisTemplate);
        DeviceInfoMapper mapper = session.getMapper(DeviceInfoMapper.class);

        List<DeviceRepository> list = mapper.selectDeviceInfo();
        if(0 == list.size()){
            return false;
        }else{
            for(DeviceRepository deviceInfo : list){
                //log.info("REDIS DATA : {} - {}",deviceInfo.getUsrId(), redisUtil.getData(deviceInfo.getUsrId()));
                log.info("DB    DATA : {} - {}",deviceInfo.getUsrId(), deviceInfo.getTokenPrivate());
                /*if(redisUtil.getData(deviceInfo.getUsrId()).isEmpty()){
                    log.info("Sync Device Info : isEmpty");
                    redisUtil.setData(deviceInfo.getUsrId(),deviceInfo.getTokenPrivate(),0L);
                }else if(redisUtil.getData(deviceInfo.getUsrId()) != deviceInfo.getTokenPrivate()){
                    log.info("Sync Device Info : isDifferent");
                    redisUtil.setData(deviceInfo.getUsrId(),deviceInfo.getTokenPrivate(),0L);
                }*/
            }
            return true;
        }
    }
}