package com.tuling.crypto;

/**
 * @Author:zhgw
 * @Date:16:56,2023/9/5
 */
public class Visiable {
    private boolean flag = true;
    private int num = 0;
    public void grow() {
        System.out.println(Thread.currentThread().getName() + "开始执行");

        while (flag) {
            num++;
            System.out.println(num);
        }
        System.out.println(Thread.currentThread().getName() + "被阻止成功，跳出循环，num：" + num);
    }

    public void halt() {
        flag = false;
        System.out.println(Thread.currentThread().getName() + "阻止增长 flag：" + flag);
    }

    public static void main(String[] args) throws InterruptedException {
        Visiable v = new Visiable();
        Thread tGrow = new Thread(() -> v.grow(), "增长线程");
        tGrow.start();
        Thread.sleep(1000);
        Thread tHalt = new Thread(() -> v.halt(),"阻止线程");
        tHalt.start();
    }
}
