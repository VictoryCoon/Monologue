package com.victory.server.service.impl;

import com.victory.server.mapper.MsgMapper;
import com.victory.server.repository.MsgRepository;
import com.victory.server.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        msgRepository.setRegId("victory");
        msgRepository.setStatus('R');
        msgRepository.setStatusCode('0');
        try{
            success += msgMapper.insertMsgLog(msgRepository);
            //log.info("Insert Two ways Transactions  I : {}",success);
            success += msgMapper.insertMsgTrace(msgRepository);
            //log.info("Insert Two ways Transactions II : {}",success);
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
