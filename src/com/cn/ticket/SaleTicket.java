package com.cn.ticket;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个售票员 卖出30张票
 * 线程 操作 资源类
 */
class Ticket {
    private int number = 30;
    private final ReentrantLock lock = new ReentrantLock();//可重入锁（一把锁），细化锁定行号

    //资源类自带方法
    public void saleTicket() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (number--) + "还剩下" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicket {
    public static void main(String[] args) throws Exception {
        Ticket ticket = new Ticket();
        //线程1
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.saleTicket();
            }
        }, "线程1");
        //线程2
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.saleTicket();
            }
        }, "线程2");
        t1.start();
        t2.start();
    }
}
