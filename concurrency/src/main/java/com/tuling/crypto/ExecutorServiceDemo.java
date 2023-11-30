package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author:zhgw
 * @Date:14:53,2023/11/28
 */
@Slf4j
public class ExecutorServiceDemo {

    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //添加任务
        CompletionService<Integer> service = new ExecutorCompletionService<Integer>(executorService);
        service.submit(() -> getPrice1());
        service.submit(() -> getPrice2());

        /**
         * 先执行完的结果先被获取到
         */
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println(service.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
    static Integer getPrice1() throws InterruptedException {
        Thread.sleep(5000);
        return 1000;
    }
    static Integer getPrice2() throws InterruptedException {
        Thread.sleep(1000);
        return 3000;
    }

}
