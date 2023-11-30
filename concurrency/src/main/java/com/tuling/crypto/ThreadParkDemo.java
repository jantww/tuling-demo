package com.tuling.crypto;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author:zhgw
 * @Date:15:13,2023/10/26
 */
public class ThreadParkDemo implements Runnable{

    private static boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while(flag) {
            System.out.println("线程被阻塞第" + ++i + "次");
            LockSupport.park();
        }
        System.out.println("线程被唤醒");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new ThreadParkDemo());
        t1.start();
        Long start = System.currentTimeMillis();
        while (flag) {
            Thread.sleep(1000);
            LockSupport.unpark(t1);
            if((System.currentTimeMillis() - start)/1000 >= 3) {
                flag = false;
            }
        }
//        flag = false;
    }
}
