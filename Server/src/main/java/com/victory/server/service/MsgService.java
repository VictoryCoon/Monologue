package com.victory.server.service;
import com.victory.server.repository.MsgRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MsgService {
    public List<MsgRepository> selectList();
    public boolean insertMsgLog(MsgRepository msgRepository);
}
