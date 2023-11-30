package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zhgw
 * @Date:15:03,2023/11/9
 */
@Slf4j
public class Semaphone {
    private static Semaphore semaphore = new Semaphore(6);

//    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(500));

    private static void exec() {
        try {
            semaphore.acquire(1);
            log.info("try to exec ...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release(1);
        }

    }
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < 7; i++) {
                exec();
            }
        }).start();

//        Thread.sleep(15000);
//        new Thread(() -> {
//            semaphore.release(1);
//        }).start();

    }
}
