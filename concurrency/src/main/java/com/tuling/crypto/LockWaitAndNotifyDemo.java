package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author:zhgw
 * @Date:10:24,2023/11/6
 */
@Slf4j
public class LockWaitAndNotifyDemo {
    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(1000l);
                    log.info(Thread.currentThread().getName() + " begin wait...");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("The thread " + Thread.currentThread().getName() + " has been notified.");

        },"Thread A").start();

        new Thread(() -> {
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + " begin notify...");
            synchronized (lock) {
                lock.notifyAll();
            }
            log.info(Thread.currentThread().getName() + " has launched notify.");
        },"Thread B").start();

    }
}
