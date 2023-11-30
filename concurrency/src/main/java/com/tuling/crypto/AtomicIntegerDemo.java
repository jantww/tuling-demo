package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:zhgw
 * @Date:14:12,2023/11/3
 */
@Slf4j
public class AtomicIntegerDemo {
    private static volatile int i = 0;
    private static AtomicInteger atomic = new AtomicInteger(0);
    public static void main(String[] args) {
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10000; k++) {
                new Thread(() -> {
                    i++;
                }).start();
            }
        }

        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int k = 0; k < 1000; k++) {
                    atomic.incrementAndGet();
                }
            }).start();
        }
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Common adder result: {}", i);
        log.info("Atomic int add result: {}", atomic.get());
    }

}
