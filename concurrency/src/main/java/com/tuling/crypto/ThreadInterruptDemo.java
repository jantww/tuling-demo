package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:18:27,2023/10/25
 */
public class ThreadInterruptDemo {
    private static int i;

    public static void main(String[] args) {
        new Thread(() -> {
            while (i < 10) {
                System.out.println(i);
                Thread.interrupted();
//                Thread.currentThread().isInterrupted();
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("==Current thread interupted");
                }
                i++;
            }
        }).start();

    }
}
