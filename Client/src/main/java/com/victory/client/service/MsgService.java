package com.victory.client.service;
import com.victory.client.repository.MsgRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MsgService {
    public List<MsgRepository> selectList();
    public boolean insertMsgLog(MsgRepository msgRepository);
}
