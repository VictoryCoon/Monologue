package com.victory.server.tcp;

import com.victory.server.queue.MsgQueue;
import com.victory.server.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpSender extends Thread {
    private final int id;
    private final MsgQueue msgQueue;
    private final int _inData;
    private final byte[] _data;

    public TcpSender(int id, MsgQueue msgQueue, int _inData, byte[] _data) {
        this.id = id;
        this.msgQueue = msgQueue;
        this._inData = _inData;
        this._data = _data;
    }
    @Override
    public void run() {
        SplitUtil splitUtil = new SplitUtil();
        StringBuilder messageClient = new StringBuilder();
        log.info("OUTPUT STREAM : {}",this._data.length);
        messageClient.append(new String(this._data, 0, this._inData));
        log.info("OUTPUT STREAM : {}", splitUtil.splitInfoMsgRepository(messageClient));
        msgQueue.enQueue(splitUtil.splitInfoMsgRepository(messageClient));
    }
}
