package com.victory.server.listener;

import com.victory.server.queue.MsgQueue;
import com.victory.server.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

@Slf4j
public class TcpSender extends Thread {
    private final int id;
    private final MsgQueue msgQueue;
    private final int _inData;
    private final byte[] _data;
    private final Lock lock;

    public TcpSender(int id, MsgQueue msgQueue, int _inData, byte[] _data, Lock lock) {
        this.id = id;
        this.msgQueue = msgQueue;
        this._inData = _inData;
        this._data = _data;
        this.lock = lock;
    }
    @Override
    public void run() {
        SplitUtil splitUtil = new SplitUtil();
        StringBuilder messageClient = new StringBuilder();
        messageClient.append(new String(this._data, 0, this._inData));
        msgQueue.enQueue(splitUtil.splitInfoMsgRepository(messageClient));
    }
}
