package com.victory.server.service.impl;

import com.victory.server.mapper.MsgMapper;
import com.victory.server.repository.MsgRepository;
import com.victory.server.service.MsgService;
import com.victory.server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MsgServiceImpl implements MsgService {

    //@Autowired
    //MsgMapper msgMapper;    // sqlSession?
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<MsgRepository> selectList() {
        SqlSession session = sqlSessionFactory.openSession(false);
        MsgMapper msgMapper = session.getMapper(MsgMapper.class);
        List<MsgRepository> msgList = new ArrayList<>();
        msgList = msgMapper.selectMsgList();
        try{
            int i = 0;
            msgList = msgMapper.selectMsgList();
            for(MsgRepository msgRepository : msgList){
                msgMapper.deleteQueueMsg(msgRepository);
                //log.info("Delete Transactions : {}, {}",i, msgRepository.getMsgId());
                ++i;
            }
            session.commit();
        }catch(Exception e){
            session.rollback();
        }finally {
            session.commit();
            session.close();
        }
        return msgList;
    }

    @Override
    public boolean insertMsgLog(MsgRepository msgRepository) {
        int success = 0;
        SqlSession session = sqlSessionFactory.openSession(false);
        MsgMapper msgMapper = session.getMapper(MsgMapper.class);
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        msgRepository.setRegId("victory");
        msgRepository.setStatus('R');
        msgRepository.setStatusCode('0');
        msgRepository.setTokenPrivate(redisUtil.getPrivateKeyData(msgRepository.getUsrId()));
        msgRepository.setTokenPublic(redisUtil.getPublicKeyData(msgRepository.getUsrId()));
        try{
            success += msgMapper.insertMsgLog(msgRepository);
            success += msgMapper.insertMsgTrace(msgRepository);
            session.commit();
        }catch(Exception e){
            session.rollback();
        }finally {
            session.commit();
            session.close();
        }
        return success == 2;
    }
}
