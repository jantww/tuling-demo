package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author:zhgw
 * @Date:16:15,2023/11/14
 */
@Slf4j
public class SynchronousQueueDemo {
    private static BlockingQueue synchronousQueue = new SynchronousQueue();

    public static void main(String[] args) throws InterruptedException{
//        addTest();
        Thread t1 = new Thread(() -> {
            putTest(1);
        });

        Thread t2 = new Thread(() -> {
            putTest(2);
        });

        t1.start();
        t2.start();

//        LockSupport.unpark(t1);
        Thread.sleep(5000);
        new Thread(() -> {
            try {
                log.info("{} get value: {}", Thread.currentThread().getName(), takeItem());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println(t1.getState());
        System.out.println(t2.getState());

    }

    private static void addTest() {
        synchronousQueue.add(1);
    }

    private static void putTest(Integer item){
        try {
            synchronousQueue.put(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Object takeItem() throws InterruptedException {
        Object o = synchronousQueue.take();
        return o;
    }





}
