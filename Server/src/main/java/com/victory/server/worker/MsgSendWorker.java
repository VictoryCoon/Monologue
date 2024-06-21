package com.victory.server.worker;

import com.victory.server.queue.MsgQueue;
import com.victory.server.repository.MsgRepository;
import com.victory.server.service.MsgService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MsgSendWorker implements Runnable{
    private final MsgService msgService;
    private final int id;
    private final MsgQueue msgQueue;

    public MsgSendWorker(MsgService msgService, int id, MsgQueue msgQueue){
        this.msgService = msgService;
        this.id = id;
        this.msgQueue = msgQueue;
    }

    @Override
    public void run() {
        while (true){
            try {
                while(!msgQueue.isEmpty()){
                    msgService.insertMsgLog(msgQueue.deQueue());
                }
                log.info("Service is running");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}