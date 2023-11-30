package com.tuling.crypto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.DoubleUnaryOperator;

/**
 * @Author:zhgw
 * @Date:10:54,2023/11/10
 */
@Slf4j
public class CountDownLatchRunner {
    private static CountDownLatch countDownLatch;
    private static void preRun() throws InterruptedException {
        countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(Thread.currentThread().getName() + " already prepared...");
                countDownLatch.countDown();

            },"Runner" + i).start();
        }

        countDownLatch.await();

    }

    @SneakyThrows
    private static void startRun() {
        Map<String, Double> scores = new ConcurrentHashMap<>();
        countDownLatch = new CountDownLatch(1);
        CountDownLatch countDownForScore = new CountDownLatch(5);
        Thread.sleep(5000);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(Thread.currentThread().getName() + " start to run...");
                countDownForScore.countDown();
                double score = Math.random()*40+60;
                scores.put(Thread.currentThread().getName(), score);
                log.info("{} scores: {}", Thread.currentThread().getName(), score );
            }, "Runner" + i).start();
        }
        countDownLatch.countDown();
        countDownForScore.await();
        Thread.sleep(5000);
        log.info("Runners average score : {}", scores.values().stream().mapToDouble(Double::doubleValue).average().getAsDouble());
    }

    public static void main(String[] args) throws InterruptedException {
        preRun();
        Thread.sleep(5000);
        log.info("All ready, go!");
        startRun();
    }
}
