package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:17:42,2023/11/8
 */
@Slf4j
public class ReenTrantLockTryFail {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() ->{
            log.info(Thread.currentThread().getName() + " try to get lock...");
            try {
                if(!lock.tryLock(3, TimeUnit.SECONDS)) {
                    log.info(Thread.currentThread().getName() + " did not get lock");
                    return;
                }
                log.info(Thread.currentThread().getName() + " has got the lock ");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }, "T1");

        //main thread get lock
        lock.lock();
        try {
            t1.start();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
