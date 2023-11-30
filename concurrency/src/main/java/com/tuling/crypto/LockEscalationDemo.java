package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Author:zhgw
 * @Date:10:51,2023/11/7
 */
@Slf4j
public class LockEscalationDemo {
    public static void main(String[] args) {
        System.setProperty("jdk.attach.allowAttachSelf", String.valueOf(true));
        Object lock = new Object();
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("{} :after 3 seconds, begin to lock", threadName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
            log.info("{} :current lock information: {}", threadName, ClassLayout.parseInstance(lock).toPrintable());
            synchronized (lock) {
                log.info("{} begin to lock...", threadName);
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                log.info("{} begin release lock...", threadName);
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }, "Thread A").start();
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("run over, and the lock information show :");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

    }
}
