package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:15:17,2023/11/8
 */
@Slf4j
public class ReenTrantLockInterruptDemo {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                try {
                    log.info(Thread.currentThread().getName() + " catch the lock...");
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                log.info("The thread catch the interruptation when wait lock...");
                e.printStackTrace();
            }

        }, "t1");

        try {
            lock.lock();
            log.info("The main thread get the lock priority...");
            t1.start();
            Thread.sleep(2000);
            t1.interrupt();
            log.info("The thread t1 has been interrupted...");
        }finally {
            lock.unlock();
        }
    }

}
