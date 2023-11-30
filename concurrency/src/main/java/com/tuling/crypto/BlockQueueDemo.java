package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author:zhgw
 * @Date:17:15,2023/11/13
 */
@Slf4j
public class BlockQueueDemo {
    private static ArrayBlockingQueue queue = new ArrayBlockingQueue(4) ;
    public static void main(String[] args) throws InterruptedException {
//        addTest();
//        offerTest();
//        putTest();
//        takeTest();
//        pollTest();
//        peekTest();
//        removeTest();
//        elementTest();
//        new Thread(()-> {
//            putTest();
//        }).start();

        new Thread(()-> {
            peekTest();
        }).start();

    }

    static {
        queue.add(1);
        queue.add(2);
    }

    private static void addTest() throws InterruptedException {
        /**
         * throw exception at 3
         */

        queue.add(3);
        queue.add(4);
        queue.add(5);
    }

    private static void offerTest() {
        /**
         *   return false at 3；
         */
        queue.offer(3);
        queue.offer(4);
        if(!queue.offer(5)) {
            log.error("Queue fully!");
        }
    }

    private static void putTest() {
        /**
         * blocked at 3
         */
        try {
            queue.put(3);
            queue.put(4);
            queue.put(5);
        }catch (InterruptedException e) {
            log.error("Queue fully!");
        }
    }

    private static void takeTest() throws InterruptedException {
        /**
         * blocked at 3;
         */
        Object o = queue.take();
        o = queue.take();
        o = queue.take();

    }

    private static void pollTest() {
        /**
         * return null at 3;
         */
        Object o1 = queue.poll();
        Object o2 = queue.poll();
        Object o3 = queue.poll();
        log.info("values: {} || {} || {}", o1,o2,o3);
    }

    private static void peekTest() {
        /**
         * always index o,  Object would not take out
         */
        Object o1 = queue.peek();
        Object o2 = queue.peek();
        Object o3 = queue.peek();
        log.info("values: {} || {} || {}", o1,o2,o3);

    }

    private static void removeTest() {
        /**
         * return false at 3
         */
        boolean out = queue.remove(3);
        out = queue.remove(4);
        out = queue.remove(5);
        if(!out) {
            log.error("Empty queue!");
        }
    }
    private static void elementTest() {
        /**
         * get from head, would not out, throw exception at 3;
         */
        Object o1 = queue.element();
        Object o2 = queue.element();
        Object o3 = queue.element();
        queue.clear();
        queue.element();
        log.info("values : {} || {} || {}", o1,o2,o3);

    }


}
