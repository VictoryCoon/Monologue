package com.victory.server.worker;

import com.victory.server.queue.MsgQueue;
import com.victory.server.repository.MsgRepository;
import com.victory.server.service.MsgService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.locks.Lock;


@Slf4j
public class MsgSelectWorker implements Runnable {

    private final MsgService msgService;
    private final int id;
    private final MsgQueue msgQueue;
    //private final Lock lock;

    public MsgSelectWorker(MsgService msgService, int id, MsgQueue msgQueue/*, Lock lock*/){
        this.msgService = msgService;
        this.id = id;
        this.msgQueue = msgQueue;
        //this.lock = lock;
    }

    @Override
    public void run() {
        while (true){
            try {
                //lock.lock();
                List<MsgRepository> list = msgService.selectList();
                if(list.isEmpty()){
                    Thread.sleep(1000);
                }else{
                    for(MsgRepository msg : list){
                        msgQueue.enQueue(msg);
                    }
                }
                //lock.unlock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
