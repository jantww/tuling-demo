package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:17:35,2023/10/25
 */
public class ThreadStopDemo {

    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + "获取锁");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        Thread.sleep(2000);
        thread.stop();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "获取锁");
            }
        }).start();

    }
}
