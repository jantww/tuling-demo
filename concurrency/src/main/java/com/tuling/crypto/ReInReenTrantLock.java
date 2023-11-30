package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:14:50,2023/11/8
 */
@Slf4j
public class ReInReenTrantLock {
    private static int sum = 0;
    private static int count = 0;
    private  static ReentrantLock lock = new ReentrantLock();

    private static void step() {
        log.info(Thread.currentThread().getName() + " begin to in lock " + (count + 1) + " times");
        lock.lock();
        try {
            for (int i = 0; i < 1000; i++) {
                sum++;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            count++;
            lock.unlock();
        }

        if(count < 3) {
            step();
        }
    }

    public static void main(String[] args) {
        step();
        log.info("Finally num : {}", sum);

    }

}
