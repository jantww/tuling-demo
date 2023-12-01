package com.tuling.crypto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
*@Author:zhgw
*@Date:11:24,2023/11/30
*
*/
@Slf4j
public class CompleteTeaDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        makingTea();
        makingTea2();
    }

    private static void makingTea() throws ExecutionException, InterruptedException {
        FutureTask<String> task2 = new FutureTask<>(new Task2());
        FutureTask<String> task1 = new FutureTask<>(new Task1(task2));

        new Thread(task1).start();
        new Thread(task2).start();
        System.out.println(task1.get());
    }

    static class Task1 implements Callable<String> {
        private FutureTask<String> t2;

        public Task1(FutureTask<String> t2) {
            this.t2 = t2;
        }

        @Override
        public String call() throws InterruptedException, ExecutionException {
            log.info("洗水壶。。。");
            Thread.sleep(3000);
            log.info("烧开水。。。");
            TimeUnit.MILLISECONDS.sleep(15000);

            return "开始泡茶：" + t2.get();
        }
    }

    static class Task2 implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("洗茶杯。。。");
            TimeUnit.SECONDS.sleep(1);
            log.info("洗茶壶。。。");
            TimeUnit.SECONDS.sleep(2);
            log.info("拿茶叶。。。");
            TimeUnit.SECONDS.sleep(3);

            return "龙井";
        }
    }

    private static void makingTea2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() ->{

                try {
                    log.info("洗水壶。。。");
                    Thread.sleep(3000);
                    log.info("烧开水。。。");
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "开始泡茶：";
            }
        );

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @SneakyThrows
            @Override
            public String get() {
                log.info("洗茶杯。。。");
                TimeUnit.SECONDS.sleep(1);
                log.info("洗茶壶。。。");
                TimeUnit.SECONDS.sleep(3);
                log.info("拿茶叶。。。");
                TimeUnit.SECONDS.sleep(3);

                return "龙井";
            }
        });
        CompletableFuture<String> result = task1.thenCombineAsync(task2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return String.format("%s%s", s, s2);
            }
        },Executors.newFixedThreadPool(3));
        System.out.println(result.get());

    }
}
