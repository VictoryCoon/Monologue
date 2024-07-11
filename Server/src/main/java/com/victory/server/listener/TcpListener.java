package com.victory.server.listener;

import com.victory.server.queue.MsgQueue;
import com.victory.server.util.SplitUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

@Slf4j
public class TcpListener implements Runnable {
    //private final int id;
    private final MsgQueue msgQueue;
    private final int bufferSize;

    public TcpListener(/*int id,*/MsgQueue msgQueue, int bufferSize) {
        //this.id = id;
        this.msgQueue = msgQueue;
        this.bufferSize = bufferSize;
    }

    @SneakyThrows
    @Override
    public void run() {
        SplitUtil splitUtil = new SplitUtil();
        Charset charset = Charset.forName("EUC-KR");
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost",21000));
        serverSocketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(this.bufferSize);
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            StringBuilder sb = new StringBuilder();
            if(null != socketChannel){
                int inputBytes = socketChannel.read(buffer);
                sb.append(new String(buffer.array(),0,inputBytes));
                log.info("SB : {}",sb);
                log.info("SB length : {}",sb.length());
                this.msgQueue.enQueue(splitUtil.splitInfoMsgRepository(sb));
            }
            buffer.clear();
            Thread.sleep(1000);
        }
    }

    private void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException{
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        log.info("Connected new Client");
    }

    private void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException{
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        log.info("BUFFER CHECK : {}",buffer);
        if(new String(buffer.array()).trim().trim().equals("EXIT")){
            client.close();
            log.info("Disconnected Client");
        }
        buffer.flip();
        client.write(buffer);
        buffer.clear();
    }
}
