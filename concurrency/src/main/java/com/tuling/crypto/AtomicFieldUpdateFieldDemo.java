package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author:zhgw
 * @Date:16:00,2023/11/3
 */
@Slf4j
public class AtomicFieldUpdateFieldDemo {

    static class Candidate {
        public volatile int vote;
    }
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static volatile int check;
    private final static AtomicIntegerFieldUpdater  fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "vote");
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        Candidate candidate = new Candidate();
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        synchronized(new Object()) {
                            check++;
                        }
                        fieldUpdater.incrementAndGet(candidate);
                        atomicInteger.incrementAndGet();
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("After increment, the candidate vote is : {}, actually should be : {}, the check value is :{}", candidate.vote, atomicInteger.get(), check);

    }


}

