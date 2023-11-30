package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Author:zhgw
 * @Date:17:54,2023/11/7
 */
@Slf4j
public class SynchronizedUpperDemo {
    public static void main(String[] args) throws InterruptedException {
        log.info("show the upping to light lock...");
        uppLightOrHeight(null);
        log.info("show the upping to heihet lock...");
        uppLightOrHeight(1l);
    }

    private static void uppLightOrHeight(Long sleep) throws InterruptedException {
        Object lock = new Object();
        log.info("At beginning, the lock info :\n{}", ClassLayout.parseInstance(lock).toPrintable());
        Thread.sleep(4000);
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            log.info(threadName + " begin to get lock ... \n {}", ClassLayout.parseInstance(lock).toPrintable());
            synchronized (lock) {
                log.info(threadName + "locking ... \n {}", ClassLayout.parseInstance(lock).toPrintable());
            }
            log.info(threadName + "released lock...\n {}", ClassLayout.parseInstance(lock).toPrintable());
        }, "Thread A").start();

        if(null != sleep) {
            Thread.sleep(sleep);
        }

        new Thread(() -> {
            String tName = Thread.currentThread().getName();
            log.info(tName + " is beginning to get lock...\n {}", ClassLayout.parseInstance(lock).toPrintable());
            synchronized (lock) {
                log.info(tName + "locking ...\n {}", ClassLayout.parseInstance(lock).toPrintable());
            }
            log.info(tName + " released lock...\n {}", ClassLayout.parseInstance(lock).toPrintable());
        }, "Thread B").start();

        Thread.sleep(5000);
        log.info("Both thread finished ...\n {}", ClassLayout.parseInstance(lock).toPrintable());
    }
}
