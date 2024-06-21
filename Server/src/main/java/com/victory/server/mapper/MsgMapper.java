package com.victory.server.mapper;

import com.victory.server.repository.MsgRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MsgMapper {
    public int selectOne();
    public List<MsgRepository> selectMsgList();
    public int deleteQueueMsg(MsgRepository msgRepository);
    public int insertMsgLog(MsgRepository msgRepository);
    public int insertMsgTrace(MsgRepository msgRepository);
}
