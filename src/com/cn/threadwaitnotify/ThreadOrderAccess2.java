package com.cn.threadwaitnotify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 高内聚自身 -- 等待判断 -- 干活-- 通知--标志位.
 * 多线程之间 ，按照顺序调用，实现 A->B->C 线程启动，要求如下：
 * <p>
 * AA打印5次 BB打印10次 CC打印15 次 。。。来10轮
 */
class Zy_ {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void getA() throws Exception {
        lock.lock();
        try {
            while (number != 0) {
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("AA");
            }
            number = 1;
            System.out.println("A number" + number);
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void getB() throws Exception {
        lock.lock();
        try {
            while (number != 1) {
                condition2.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("BB");
            }
            number = 2;
            System.out.println("B number" + number);
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void getC() throws Exception {
        lock.lock();
        try {
            while (number != 2) {
                condition3.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("CC");
            }
            number = 0;
            System.out.println("c number" + number);
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadOrderAccess2 {
    public static void main(String[] args) {
        Zy1 zy = new Zy1();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    zy.getA();
                    System.out.println("第" + i + "轮打印A");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "线程1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    zy.getB();
                    System.out.println("第" + i + "轮打印B");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "线程2");
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    zy.getC();
                    System.out.println("第" + i + "轮打印C");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "线程3");

        t1.start();
        t2.start();
        t3.start();
    }

}
