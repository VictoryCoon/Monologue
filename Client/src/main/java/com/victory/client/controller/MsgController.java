package com.victory.client.controller;

import com.victory.client.repository.MsgRepository;
import com.victory.client.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

//@Controller // for view
@RestController // for json
@Slf4j
public class MsgController implements HandlerInterceptor {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    MsgService msgService;

    @GetMapping(value = "/kafkaProduce")
    //@RequestBody
    public ResponseEntity<?> kafkaProduce(){
        log.info("KAFKA CALL");
        kafkaTemplate.send("monoTopic","TEST");

        return ResponseEntity.ok("TEST");
    }

    @GetMapping(value = "/dbCall")
    public ResponseEntity<?> dbCall(){
        List<MsgRepository> msg = msgService.selectList();
        log.info("DB CALL : {}",msg);
        return ResponseEntity.ok(msg);
    }
}
