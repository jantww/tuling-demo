package com.tuling.crypto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
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

        CompletableFuture<Integer> futureApply = CompletableFuture.supplyAsync(() -> {
            Integer number = new Random().nextInt();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("First period value: {}", number);
            return number;
        });
        CompletableFuture<Integer> secondSupply = futureApply
                .thenApplyAsync(number -> {
            int secondNumber = number * 3;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Second period value: {}", secondNumber);
            return secondNumber;
        });
        log.info("Last value:{}", secondSupply.get());

    }
}
