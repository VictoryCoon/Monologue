package com.victory.server.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumeWorker {
    @KafkaListener(topics = "monoTopic", groupId = "monoGroup")
    public void listener(Object data){
        log.info("CONSUME : {}",data);
    }
}
