package com.victory.server.worker;

import com.victory.server.queue.MsgQueue;
import com.victory.server.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class KafkaConsumeWorker {

    @Autowired
    MsgQueue queue;

    @KafkaListener(topics = "monoTopic", groupId = "monoGroup")
    public void listener(String data){
        //MsgQueue queue = new MsgQueue();
        SplitUtil splitUtil = new SplitUtil();
        log.info("CONSUME STR : {}", data);
        StringBuilder sb = new StringBuilder();
        sb.append(data);
        log.info("CONSUME STB : {}", splitUtil.splitInfoMsgRepository(sb));
        queue.enQueue(splitUtil.splitInfoMsgRepository(sb));
    }
}
