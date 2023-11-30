package com.tuling.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:11:17,2023/11/15
 */
@Slf4j
public class DelayQueueDemo {
    private static ReentrantLock lock = new ReentrantLock();

    private static DelayQueue<DelayInfo> delayQueue = new DelayQueue<DelayInfo>();

    private static final Long now = System.currentTimeMillis();

    @AllArgsConstructor
    static class DelayInfo implements Delayed {
        private Long delay;

        @Override
        public long getDelay(TimeUnit unit) {
           return unit.toNanos(System.currentTimeMillis() - (now+delay));
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        offerTest();
        takeTest();
    }

    private static void offerTest() {
        delayQueue.offer(new DelayInfo(10000l));
        delayQueue.offer(new DelayInfo(5000l));
    }

    private static void takeTest() throws InterruptedException {

        DelayInfo delayInfo = (DelayInfo) delayQueue.take();
        DelayInfo delayInfo1 = (DelayInfo) delayQueue.take();
//        DelayInfo delayInfo2 = (DelayInfo) delayQueue.take();
        log.info("delay time infos: {}, {}, {}", delayInfo.delay, delayInfo1.delay);
    }



}
