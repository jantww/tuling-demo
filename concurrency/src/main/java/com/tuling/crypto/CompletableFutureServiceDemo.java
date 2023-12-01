package com.tuling.crypto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Author:zhgw
 * @Date:18:06,2023/11/28
 */
@Slf4j
public class CompletableFutureServiceDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            int random = new Random().nextInt();
//            if (random % 2 == 0) {
//                int i = random / 0;
//            }
//            return "test";
//        });
//
//        completableFuture.exceptionally(new Function<Throwable, String>() {
//            @Override
//            public String apply(Throwable throwable) {
//                System.out.println("ran cross the exception: " + throwable.getMessage());
//                return "test failed";
//            }
//        });
//
//        completableFuture.whenComplete(new BiConsumer<String, Throwable>() {
//            @Override
//            public void accept(String s, Throwable throwable) {
//                if (s != null) {
//                    System.out.println(s + " ran over");
//                }
//            }
//        });
//        Thread.sleep(1000);
//
//        CompletableFuture<Integer> futureApply = CompletableFuture.supplyAsync(() -> {
//            Integer number = new Random().nextInt();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("First period value: {}", number);
//            return number;
//        });
//        CompletableFuture<Integer> secondSupply = futureApply
//                .thenApply(number -> {
//            int secondNumber = number * 3;
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("Second period value: {}", secondNumber);
//            return secondNumber;
//        });
//        log.info("Last value:{}", secondSupply.get());
//
//        CompletableFuture<Void> futureAccept = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return new Random().nextInt();
//        }).thenAccept(number -> {
//            int result = number * 3;
//            log.info("Last value :{}", result);
//        });
//        log.info("The future get last result :{}", futureAccept.get());
//        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
//            int time = new Random().nextInt(3000)+1;
//            try {
//                TimeUnit.MILLISECONDS.sleep(time);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("First period time:{}", time);
//            return time;
//        });
//        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
//            int time = new Random().nextInt(1000);
//            try {
//                TimeUnit.MILLISECONDS.sleep(time);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("Second period time: {}", time);
//            return time;
//        });
//
//        CompletableFuture<Void> acceptBothFuture = future1.thenAcceptBoth(future2, new BiConsumer<Integer, Integer>() {
//            @SneakyThrows
//            @Override
//            public void accept(Integer i1, Integer i2) {
//                TimeUnit.MILLISECONDS.sleep(1000);
//                log.info("The addition accept both result :{}", i1 + i2);
//            }
//        });
//        acceptBothFuture.join();
//
//        CompletableFuture<Void> runFuture = CompletableFuture.supplyAsync(() -> {
//            int value = new Random().nextInt(5);
//            try {
//                TimeUnit.MILLISECONDS.sleep(value);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("First run period value:{}", value);
//            return value;
//        }).thenApplyAsync(v -> {
//            int add = new Random().nextInt(3);
//            log.info("to add number: {}", add);
//            log.info("Second run period value:{}", v+add);
//            return v+add;
//        }, Executors.newFixedThreadPool(3)).thenRun(() -> {
//                    System.out.println("The feture has run over!");
//                }
//        );

        CompletableFuture<Integer> thenCombineFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            private int value = 3;
            @SneakyThrows
            @Override
            public Integer get() {
                log.info("First value preparing...");
                TimeUnit.SECONDS.sleep(100);
                return value;
            }
        });

        CompletableFuture<Integer> thenCombineFuture2 = CompletableFuture.supplyAsync(() -> {
            log.info("Second value preparing...");
            int value2 = 5;
            try {
                TimeUnit.SECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return value2;
        });
        CompletableFuture<Integer> combines = thenCombineFuture.thenCombine(thenCombineFuture2, new BiFunction<Integer, Integer, Integer>() {

            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer+integer2;
            }
        });
        log.info("Combine future result : {}", combines.get());

//        CompletableFuture<Integer> eitherFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                int time = new Random().nextInt(100);
//                try {
//                    TimeUnit.MILLISECONDS.sleep(time);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("No.1 cost :{} milli seconds", time);
//                return time;
//            }
//        });
//
//        CompletableFuture<Integer> eitherFuture1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                int time = new Random().nextInt(1000);
//                try {
//                    TimeUnit.MILLISECONDS.sleep(time);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("No.2 cost :{} milli seconds",time);
//                return time;
//            }
//        });
//
//        CompletableFuture<Integer> eitherResult = eitherFuture.applyToEither(eitherFuture1, new Function<Integer, Integer>() {
//            @Override
//            public Integer apply(Integer i) {
//                System.out.println("The first catch time :" + i);
//                return i;
//            }
//        });
//
//        eitherResult.join();
//
//        CompletableFuture<Integer> bothFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//
//            @SneakyThrows
//            @Override
//            public Integer get() {
//                int time = new Random().nextInt(1000);
//                TimeUnit.MILLISECONDS.sleep(time);
//                log.info("First period cost :{} milli seconds", time);
//                return time;
//            }
//        });
//
//        CompletableFuture<Integer> bothFuture1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//            @SneakyThrows
//            @Override
//            public Integer get() {
//                int time = new Random().nextInt(10);
//                TimeUnit.MILLISECONDS.sleep(time);
//                log.info("Second period cost: {} milli seconds", time);
//                return time;
//            }
//        });
//
//        CompletableFuture<Void> bothResult = bothFuture.runAfterBoth(bothFuture1, () -> {
//            System.out.println("Both of task have run over.");
//        });
//        bothResult.join();
    }
}
