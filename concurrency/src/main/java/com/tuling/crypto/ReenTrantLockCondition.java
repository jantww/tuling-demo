package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:11:39,2023/11/9
 */
@Slf4j
public class ReenTrantLockCondition {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition cigCon = lock.newCondition();
    private static boolean hasCig = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                lock.lock();
                for (;;) {
                    Thread.sleep(2000);
                    System.out.println("There is no cigarette, have a rest...");
                    cigCon.await();
                    System.out.println("Cigarette exists, begin to work...");
                    hasCig = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            for (;;) {
                try {
                    lock.lock();
                    if(!hasCig) {
                        cigCon.signal();
                        log.info("Begin to give cigarette...");
                        hasCig = true;
                    }

                } finally {
                    lock.unlock();
                }
            }
        }).start();

    }
}
