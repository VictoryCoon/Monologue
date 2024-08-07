package com.victory.client.queue;

import com.victory.client.repository.MsgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

class Node<T>{
    T data = null;
    Node<T> next = null;
}

@Slf4j
@Component
public class MsgQueue{

    private Node<MsgRepository> head;
    private Node<MsgRepository> tail;
    private final int maxSize;
    private int size;


    //Default Generator
    public MsgQueue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.maxSize = 99999999;    //이건 어디선가 정해보자...
    }

    public boolean enQueue(MsgRepository msgRepository){
        if(isFull()){
            return false;
        }else{
            Node<MsgRepository> addData = new Node<MsgRepository>();
            addData.data = msgRepository;
            if(isEmpty()){
                head = addData;
            }else{
                tail.next = addData;
            }
            tail = addData;
            ++size;
            return true;
        }
    }

    public MsgRepository deQueue(){
        if(isEmpty()){
            return null;
        }else{
            MsgRepository data = head.data;
            if(head == tail){
                head = null;
                tail = null;
            }else{
                head = head.next;
            }
            --size;
            return data;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public boolean isFull(){
        if(size >= maxSize){
            log.info("IS FULL {}",size);
        }
        return size >= maxSize;
    }

    public int getSize(){
        return size;
    }

    public MsgRepository getHead(){
        if(isEmpty()){
            return null;
        }else{
            return head.data;
        }
    }
}