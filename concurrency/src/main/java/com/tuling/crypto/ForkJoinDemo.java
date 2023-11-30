package com.tuling.crypto;

import com.tuling.crypto.task.NumSum;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author:zhgw
 * @Date:17:29,2023/11/16
 */
@Slf4j
public class ForkJoinDemo {

    private static Integer[] arr = new Integer[10000000];

    static {
        for (int i = 0; i < 10000000; i++) {
            Random random = new Random();
            arr[i] = random.nextInt();
        }
    }

    public static void main(String[] args) {
        Long begin = System.currentTimeMillis();
        log.info("Begin sum job...");
        Thread commomn = new Thread(() -> {
            Integer commonResult = commonSum();
            log.info("Common calculate {} milli seconds, result: {}",System.currentTimeMillis()-begin,  commonResult);
        });

        ForkJoinPool joinPool = new ForkJoinPool();
        NumSum sum = new NumSum(arr, 0, arr.length);
        Thread fork = new Thread(() -> {
            Integer forkResult = joinPool.invoke(sum);
            log.info("Fork join calculate {} milli seconds, results:{}", System.currentTimeMillis()-begin, forkResult);
        });
        commomn.start();
        fork.start();


    }

    private static Integer commonSum() {
        Integer result = 0;
        for (int i = 0; i < arr.length; i++) {
            result = result + arr[i];
        }
        return result;
    }
}
