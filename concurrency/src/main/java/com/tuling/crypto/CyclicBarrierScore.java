package com.tuling.crypto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author:zhgw
 * @Date:15:04,2023/11/10
 */
@Slf4j
public class CyclicBarrierScore {

    private static Map<String, Double> scores = new ConcurrentHashMap<>();
    private static final int count = 10;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> {
            double value = scores.values().stream().mapToDouble(Double::doubleValue).average().getAsDouble();
            log.info("Calculating...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("The average score of students: {}", value);
        });


        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    double score = BigDecimal.valueOf(Math.random()*50 + 50).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    log.info("{} got score : {}", Thread.currentThread().getName(), score);
                    scores.put(Thread.currentThread().getName(), score);
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    log.info("after await");
                }
            }, "Student" + i).start();
        }
    }
}
