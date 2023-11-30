package com.tuling.crypto;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:14:39,2023/11/8
 */
public class SyncReenTrantLockDemo {
    private static int sum = 0;
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    for (int j = 0; j < 10000; j++) {
                        sum++;
                    }
                }finally {
                    lock.unlock();
                }

            }).start();
        }
        Thread.sleep(3000);
        System.out.println("Finally sum : " + sum);
    }

}
