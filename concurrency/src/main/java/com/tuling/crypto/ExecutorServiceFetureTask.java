package com.tuling.crypto;

import java.util.concurrent.*;

/**
 * @Author:zhgw
 * @Date:18:20,2023/11/27
 */
public class ExecutorServiceFetureTask {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    FutureTask<String> future1 = new FutureTask<>(new Task1());
    FutureTask<String> future2 = new FutureTask<>(new Task2());
    FutureTask<String> future3 = new FutureTask<>(new Task3());
    FutureTask<String> future4 = new FutureTask<>(new Task4());
    FutureTask<String> future5 = new FutureTask<>(new Task5());
    FutureTask<String> future6 = new FutureTask<>(new Task6());
    executorService.submit(future1);
    executorService.submit(future2);
    executorService.submit(future3);
    executorService.submit(future4);
    executorService.submit(future5);
    executorService.submit(future6);
    /**
     * 阻塞直至获取方法执行完结果
     */
    System.out.println(future1.get());
    System.out.println(future2.get());
    System.out.println(future3.get());
    System.out.println(future4.get());
    System.out.println(future5.get());
    System.out.println(future6.get());

  }

  static class Task1 implements Callable<String> {
    @Override
    public String call() throws Exception {
      Thread.sleep(2000);
      return "查询商品基本信息成功！";
    }
  }

  static class Task2 implements Callable<String> {

    @Override
    public String call() throws Exception {
      Thread.sleep(3000);
      return "查询商品价格成功！";
    }
  }
  static class Task3 implements Callable<String> {


    @Override
    public String call() throws Exception {
      Thread.sleep(5000);
      return "查询商品货存成功！";
    }
  }
  static class Task4 implements Callable<String> {
    @Override
    public String call() throws Exception {
      Thread.sleep(50000);
      return "查询商品图片信息成功！";
    }
  }

  static class Task5 implements Callable<String> {
    @Override
    public String call() throws Exception {
      Thread.sleep(4000);
      return "查询商品成交量信息成功！";
    }
  }
  static class Task6 implements Callable<String> {
    @Override
    public String call() throws Exception {
      Thread.sleep(1000);
      return "查询商品积分信息成功！";
    }
  }
}
