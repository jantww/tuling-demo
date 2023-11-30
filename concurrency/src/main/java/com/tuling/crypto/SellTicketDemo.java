package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:17:09,2023/10/25
 */
public class SellTicketDemo implements Runnable{

    private int ticket;

    public SellTicketDemo(int ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        if(ticket > 0) {
            synchronized (this) {
                if(ticket > 0) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在卖票，当前余票：" + --ticket);
                }
            }
        }
    }

    public static void main(String[] args) {
        SellTicketDemo demo = new SellTicketDemo(10);
        Thread thread1 = new Thread(demo,"thread1");
        Thread thread2 = new Thread(demo,"thread2");
        Thread thread3 = new Thread(demo,"thread3");
        Thread thread4 = new Thread(demo,"thread4");
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MAX_PRIORITY);
        thread4.setPriority(Thread.MAX_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

}
