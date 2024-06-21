package com.victory.server.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Data
@Getter
@Setter
@Repository
public class MsgRepository {
    /**
     * G_PUSH_SEND ONLY
     **/
    String msgId;
    String msgDt;
    String usrId;
    char   sendType;
    char   msgType;
    String msgTitle;
    String msgContent;
    String msgPayload;
    String msgFulltext;
    String tokenPrivate;
    String tokenPublic;
    String imageUrl;
    char   sound;
    /**
     * G_PUSH_SEND U G_PUSH_LOG
     **/
    String requestTime;
    String responseTime;
    String confirmTime;
    char   status;
    char   statusCode;

    /**
     * G_PUSH_TRACE
     **/
    int    traceSeq;
    /**
     * common
     **/
    String regDt;
    String regId;
    String updDt;
    String updId;
}