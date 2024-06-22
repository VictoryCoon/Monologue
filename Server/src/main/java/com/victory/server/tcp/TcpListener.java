package com.victory.server.tcp;

import com.victory.server.queue.MsgQueue;
import com.victory.server.service.MsgService;
import com.victory.server.util.SplitUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class TcpListener implements Runnable{
    private final MsgService msgService;
    private final int id;
    private final MsgQueue msgQueue;

    private final int bufferSize;


    public TcpListener(MsgService msgService, int id, MsgQueue msgQueue, int bufferSize) {
        this.msgService = msgService;
        this.id = id;
        this.msgQueue = msgQueue;
        this.bufferSize = bufferSize;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true){
            try(
                    ServerSocket socket = new ServerSocket(21000);
                    Socket listenSocket = socket.accept();
                    InputStream inputStream = listenSocket.getInputStream();
                    OutputStream outputStream = listenSocket.getOutputStream();
            ){
                byte[] _data = new byte[this.bufferSize];
                int inputBytes = inputStream.read(_data);
                SplitUtil splitUtil = new SplitUtil();
                listenSocket.setKeepAlive(true);
                //final String messageClient = new String(_data,0,inputBytes);
                StringBuilder messageClient = new StringBuilder();
                messageClient.append(new String(_data,0,inputBytes));
                //outputStream.write(messageClient.getBytes());
                //log.info("OUTPUT STREAM : {}",messageClient);
                log.info("OUTPUT STREAM : {}",splitUtil.splitInfoMsgRepository(messageClient));
                msgQueue.enQueue(splitUtil.splitInfoMsgRepository(messageClient));
                outputStream.flush();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
