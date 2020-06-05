package com.cn.lock;

import java.util.concurrent.TimeUnit;

/**
 * 线程操作资源
 */
class Phone {
    public Object lock = new Object();

    public static synchronized void send1() {
        System.out.println("发送邮件.");
            try {
                TimeUnit.SECONDS.sleep(4);//睡4秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("发送邮件");
    }

    public static synchronized void send2() {
            System.out.println("发送短信");
    }
}

public class LockInfo {
    public static void main(String[] args) {

        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            phone1.send1();
        }, "线程1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->
        {
            phone2.send2();
        }, "线程2").start();
    }

}
