package com.tuling.crypto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhgw
 * @Date:13:58,2023/11/8
 */
@Slf4j
public class ReenTrantLockConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            log.info(Thread.currentThread().getName() + " begin to execute...");
            try {
                condition.wait();
                log.info(Thread.currentThread().getName() + " execute finished.");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                lock.lock();
                try {
                    log.info(Thread.currentThread().getName() + " begin to signal the lock...");
                    Thread.sleep(2000);
                    condition.signal();
                    log.info(Thread.currentThread().getName() + " signal the thread finished...");
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        }).start();

    }
}
