package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zhgw
 * @Date:10:57,2023/11/8
 */
@Slf4j
public class ReLockCancelDemo{
    public static void main(String[] args) throws InterruptedException {
        List<Object> list = new ArrayList<>();

        Thread.sleep(5000);
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Object lock = new Object();
                synchronized (lock) {
                    list.add(lock);
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread A").start();
        Thread.sleep(1000);
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Object lock = list.get(i);
                synchronized (lock) {
                if((i>=15 && i<=19) || i >35) {
                    log.info(Thread.currentThread().getName() + " locking " + i+1 + "\n {}",ClassLayout.parseInstance(lock).toPrintable());
                    }
                }
                if(i==17 || i==19) {
                    log.info(Thread.currentThread().getName() + " released " + i+1 + " lock \n {}",ClassLayout.parseInstance(lock).toPrintable());
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread B").start();

        Thread.sleep(1000);
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Object lock = list.get(i);
                synchronized (lock) {
                    if((i>=15 && i<=19) || (i>=20 && i <=40) || i >40) {
                        log.info(Thread.currentThread().getName() + " is locking " + i+1 + "\n {}",ClassLayout.parseInstance(lock).toPrintable());
                    }
                }
                if(i==17 || (i>=18 && i <=21) || (i>=40 && i<= 45)) {
                    log.info(Thread.currentThread().getName() + "relased " + i+1 + " lock" + "\n {}",ClassLayout.parseInstance(lock).toPrintable());

                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread C").start();

        Thread.sleep(3000);
        log.info("All thread run over... catch new lock : \n {}", ClassLayout.parseInstance(new Object()).toPrintable());
    }
}

