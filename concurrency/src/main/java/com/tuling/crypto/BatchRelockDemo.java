package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author:zhgw
 * @Date:18:30,2023/11/7
 */
@Slf4j
public class BatchRelockDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        List<Object> list = new ArrayList<>();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Object lock = new Object();
                synchronized (lock) {
                    list.add(lock);
                }
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread A").start();
        Thread.sleep(3000);
        log.info("Get the twentieth lock...\n {}", ClassLayout.parseInstance(list.get(19)).toPrintable());
        new Thread(() -> {
            String tName = Thread.currentThread().getName();
            for (int i = 0; i < 40; i++) {
                Object lock = list.get(i);
                if((i>=17 && i<= 19) || i>38) {
                    synchronized (lock) {
                        log.info(tName + " locking " + i+1 +"... \n {}", ClassLayout.parseInstance(lock).toPrintable());
                    }
                }
                if(i>=18) {
                    log.info(tName + "relased " + i+1 + "...\n {}", ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        }, "Thread B").start();
        Thread.sleep(100000);
        LockSupport.park();
    }
}
