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

//    @GetMapping(value = "/")
//    public ResponseEntity<?> home(){
//        return ResponseEntity.ok("HOME");
//    }

    @GetMapping(value = "/kafkaProduce")
    public ResponseEntity<?> kafkaProduce(){
        // 20,8,16,1,1,64,2000,4000,4000,256,256,256,1
        String value01 = String.format("%-20s","20240721TEST"+String.valueOf((int)((Math.random()*89999999)+10000000)));
        String value02 = String.format("%-8s","20240721");
        String value03 = String.format("%-16s","VictoryCoon");
        String value04 = String.format("%-1s","K");
        String value05 = String.format("%-1s","S");
        String value06 = String.format("%-64s","이것은 제목 입니다.");
        String value07 = String.format("%-2000s","이것은 내용 입니다.");
        String value08 = String.format("%-4000s","이것은 페이로드 입니다.");
        String value09 = String.format("%-4000s","이것은 전문형식 입니다.");
        String value10 = String.format("%-256s","Private Tokens");
        String value11 = String.format("%-256s","Public Tokens");
        String value12 = String.format("%-256s","www.naver.com");
        String value13 = String.format("%-1s","D");
        StringBuilder sb = new StringBuilder();
        sb.append(value01);
        sb.append(value02);
        sb.append(value03);
        sb.append(value04);
        sb.append(value05);
        sb.append(value06);
        sb.append(value07);
        sb.append(value08);
        sb.append(value09);
        sb.append(value10);
        sb.append(value11);
        sb.append(value12);
        sb.append(value13);
        log.info("KAFKA CALL");
        kafkaTemplate.send("monoTopic",sb.toString());
        log.info("PADDING : {}",sb);
        return ResponseEntity.ok(sb);
    }

    @GetMapping(value = "/dbCall")
    public ResponseEntity<?> dbCall(){
        List<MsgRepository> msg = msgService.selectList();
        log.info("DB CALL : {}",msg);
        return ResponseEntity.ok(msg);
    }
}
