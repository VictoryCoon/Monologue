package com.victory.server.worker;

import com.victory.server.queue.MsgQueue;
import com.victory.server.repository.MsgRepository;
import com.victory.server.service.MsgService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class MsgWorker implements Runnable{

    private final MsgService msgService;
    private final int id;

    public MsgWorker(MsgService msgService, int id){
        this.msgService = msgService;
        this.id = id;
    }

    @Override
    public void run() {
        while (true){
            try {
                MsgQueue queue = new MsgQueue();
                List<MsgRepository> list = msgService.selectList();
                for(MsgRepository msg : list){
                    queue.enQueue(msg);
                }

                while(!queue.isEmpty()){
                    msgService.insertMsgLog(queue.deQueue());
                }
                log.info("Service is running");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
