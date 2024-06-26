package com.victory.server.tcp;

import com.victory.server.queue.MsgQueue;
import com.victory.server.service.MsgService;
import com.victory.server.util.SplitUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class TcpListener implements Runnable {
    //private final int id;
    private final MsgQueue msgQueue;
    private final int bufferSize;
    public static Object lock = new Object();

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
        ByteBuffer buffer = ByteBuffer.allocate(8192*10);
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            //log.info("????");
            StringBuilder sb = new StringBuilder();
            if(null != socketChannel){
                int inputBytes = socketChannel.read(buffer);
                sb.append(new String(buffer.array(),0,inputBytes));
                log.info("SB : {}",sb);
                log.info("SB length : {}",sb.length());
                this.msgQueue.enQueue(splitUtil.splitInfoMsgRepository(sb));
            }
            buffer.clear();
            Thread.sleep(500);
            /*
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIter = selectionKeys.iterator();

            while(keyIter.hasNext()){
                SelectionKey key = keyIter.next();
                log.info("BEFORE");
                if(key.isAcceptable()){
                    log.info("register");
                    register(selector,serverSocketChannel);
                }
                if(key.isReadable()){
                    log.info("answerWithEcho");
                    answerWithEcho(buffer,key);
                }
                keyIter.remove();
            }
            */
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
