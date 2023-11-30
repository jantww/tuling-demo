package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author:zhgw
 * @Date:15:10,2023/11/3
 */
@Slf4j
public class AtomicIntegerArrayDemo {

    private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[] {1,2,3,4,5});
    public static void main(String[] args) {
        int i0 = atomicIntegerArray.get(0);
        log.info("before cas update the 0 stitution, the value is : {}", i0);
        if(atomicIntegerArray.compareAndSet(0,1,2)) {
            log.info("After cas update the 0 stitution, the value is : {}", 2);
        }else {
            log.info("cas update the 0 stitution value faled, the expect value is :{}, actual value is :{}", 1, i0);
        }

        int i0A2 = atomicIntegerArray.addAndGet(1, 2);
        if(i0A2 == 4) {
            log.info("add 1 stitution 2 successfully,  the 1 stitution current value is :{}", i0A2);
        }

        atomicIntegerArray.set(0, 100);
        log.info("After set 0 stitutoion, now the value is :{}", atomicIntegerArray.get(0));


    }
}
