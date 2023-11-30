package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:17:24,2023/10/25
 */
public class ThreadJoinDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("子线程执行中。。。");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        long start = System.currentTimeMillis();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("子线程执行时间：" + (System.currentTimeMillis() - start));
        System.out.println("主线程执行完毕");
    }
}
