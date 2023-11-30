package com.tuling.crypto;

import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author:zhgw
 * @Date:10:11,2023/11/27
 */
@Slf4j
public class FutureTaskDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask future = new FutureTask(new Callable() {
      @Override
      public Object call() throws Exception {
        Thread.sleep(6000);
        return "获取结果";
      }
    });
    new Thread(future).start();
    boolean isDone = future.isDone();
    log.info("Check task is over: {}", isDone);
    System.out.println(future.get());
    boolean isOver = future.isDone();
    log.info("Check task is over: {}", isOver);

    Task task = new Task();

    FutureTask<Integer> futureTask = new FutureTask<>(task);
    futureTask.run();
    System.out.println(futureTask.get());


  }

  static class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
      Integer result = 0;
      for (int i = 0; i < 100; i++) {
        result += i;
      }
      return result;
    }
  }
}
