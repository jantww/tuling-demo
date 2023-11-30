package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author:zhgw
 * @Date:10:22,2023/11/3
 */
@Slf4j
public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        AtomicStampedReference atomicReference = new AtomicStampedReference(1, 1);
        int[] stampHolder = new int[1];
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            int value = (int) atomicReference.get(stampHolder);
            int stamp = stampHolder[0];
             log.info(threadName + " first get current value: {}, stamp :{}", value, stamp);
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(atomicReference.compareAndSet(value, 2, stamp, stamp+1)) {
                log.info(threadName + " cas change value from {} to {} successsfully.", value, 2);
            }else {
                log.info(threadName + " cas change value from {} to {} failed. expect value : {}, actual value: {}; expect stamp :{}, actual stamp: {}", value, 2, value, atomicReference.get(stampHolder), stamp, atomicReference.getStamp());
            }
        }).start();

        new Thread(() -> {
            int value = (int) atomicReference.get(stampHolder);
            int stamp = stampHolder[0];
            String threadName = Thread.currentThread().getName();
            log.info(threadName + " get current value : {}, current stamp : {}", value, stamp);
            if(atomicReference.compareAndSet(value, 3, stamp, stamp+1)) {
                log.info(Thread.currentThread().getName() + " cas change value from {} to {} successfully.", value, 3 );
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = (int) atomicReference.getReference();
            stamp = atomicReference.getStamp();
            log.info(threadName + " get current value : {}, current stamp : {}", value, stamp);
            if(atomicReference.compareAndSet(3,  1, stamp, stamp+1)) {
                log.info(threadName + " cas change value from : {} to : {} successfully.", 3, 1);
            }
            log.info("after " + threadName + "transfer , the value : {}, the stamp :{}", atomicReference.getReference(),atomicReference.getStamp());
        }).start();

    }

}
