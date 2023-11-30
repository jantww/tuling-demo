package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:13:45,2023/10/26
 */
public class StopThreadDemo implements Runnable{

  @Override
  public void run() {
    Object lock = new Object();
    int i = 0;
    while (!Thread.currentThread().isInterrupted() && i < 100) {
      System.out.println("线程执行中，i = " + ++i);
      synchronized (lock) {
        try {
          lock.wait(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
      }

//      try {
//        Thread.sleep(1);
//      } catch (InterruptedException e) {
//        System.out.println(e.getMessage());
//        Thread.currentThread().interrupt();
//      }
    }
    System.out.println("线程已被中断，最终 i = " + i);
  }

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(new StopThreadDemo());
    thread.start();
    Thread.sleep(3);
    thread.interrupt();
  }
}
