package com.tuling.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:zhgw
 * @Date:15:00,2023/11/1
 */
public class ABATest {
    static Logger logger = LoggerFactory.getLogger(ABATest.class);

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        new Thread(() -> {
            int a = atomicInteger.get();
            logger.info(Thread.currentThread().getName() + " get current value: {}",a);
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(atomicInteger.compareAndSet(a, 1)) {
                System.out.println(Thread.currentThread().getName() + " cas update value from " + a + " to " + 1 + " sucessfully.");
            }else  {
                System.out.println(Thread.currentThread().getName() + " cas update failed!, current :" + atomicInteger.get() + ", expect :" + 0 );
            }
        }).start();

        new Thread(() -> {
            int b = atomicInteger.get();
            System.out.println(Thread.currentThread().getName() + " get current value " + b);
            if(atomicInteger.compareAndSet(b, 2)) {
                System.out.println(Thread.currentThread().getName() + "cas change value from " + b + " to " + 2 + " successfully.");
            }
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int c = atomicInteger.get();
            if(atomicInteger.compareAndSet(atomicInteger.get(), 0)) {
                System.out.println(Thread.currentThread().getName() + " cas change value from " + c + " to " + 0 +  " successfully.");
            }
        }).start();
    }
}
