package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author:zhgw
 * @Date:10:56,2023/11/14
 */
@Slf4j
public class LinkBlockQueueDemo {
    private static LinkedBlockingQueue queueWideless = new LinkedBlockingQueue(){{add(1);add(2);}};

    public static void main(String[] args) throws InterruptedException {
//        addTest();
//        offerTest();
//        putTest();
        takeTest();
    }


    private static void addTest() {
        /**
         * If give the capacity， throw exception at 3;
         */
        queueWideless.add(3);
        queueWideless.add(4);
        queueWideless.add(5);
    }

    private static void offerTest() {
        /**
         * If give the capacity, then rteturn false at 3;
         */
        queueWideless.offer(3);
        queueWideless.offer(4);
        boolean falg =  queueWideless.offer(5);
    }


    private static void putTest() throws InterruptedException {
        /**
         * if give capacity , then blocked at 3;
         */
        queueWideless.put(3);
        queueWideless.put(4);
        queueWideless.put(5);
    }
    private static void takeTest() throws InterruptedException {
        Object o = queueWideless.take();
    }
}
