package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author:zhgw
 * @Date:10:58,2023/11/6
 */
@Slf4j
public class SynchDdemo {
    private static int num;

    private static synchronized void decrease() {
        num--;
    }
    private static synchronized void increase() {
        num++;
    }

    public static void main(String[] args) throws InterruptedException {

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increase();
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    decrease();
                }
            }
        });

        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("Finally the num value : " + num);

    }
}
