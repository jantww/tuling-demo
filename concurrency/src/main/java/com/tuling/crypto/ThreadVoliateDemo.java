package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:14:24,2023/10/26
 */
public class ThreadVoliateDemo {
  private static volatile boolean flag = true;

  public static void main(String[] args) {
    new Thread(() -> {
      while (flag) {
        System.out.println("turn off");
        flag = false;
      }
    }).start();

    new Thread(() -> {
      while(!flag) {
        System.out.println("turn on");
        flag = true;
      }
    }).start();
  }
}
