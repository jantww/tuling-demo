package com.tuling.crypto;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:zhgw
 * @Date:14:32,2023/10/26
 */
public class WaitDemo {
    private static Object lock = new Object();
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger i = new AtomicInteger();
        new Thread(() -> {
            while (flag) {
                synchronized (lock) {
                    System.out.println("线程进入等待队列 " + i.incrementAndGet());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("线程被唤醒。。。");
        }).start();

        new Thread(() -> {
            while (flag) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    if(flag) {
                        lock.notify();
                        flag = false;
                        System.out.println("唤醒线程");
                    }
                }
            }
        }).start();
    }
}
