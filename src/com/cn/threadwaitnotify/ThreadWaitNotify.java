package com.cn.threadwaitnotify;

/**
 * 现在2个线程，可以操作初始值为0 的一个变量
 * 一个线程对变量进行加1 一个线程对变量进行减1
 * 实现交替，10轮，变量初始值为0 [一个偶数，一个奇数交替]
 */
class Zy {
    private int number = 0;

    //加1
    public synchronized void increment() throws Exception {
        if (number != 0) {
            this.wait();
        }
        number++;
        System.out.println("线程：" + Thread.currentThread().getName() + "加+1之后：" + number);
        this.notifyAll();
    }

    //减1
    public synchronized void decrement() throws Exception {
        if (number == 0) {
            this.wait();
        }
        number--;
        System.out.println("线程：" + Thread.currentThread().getName() + "减-1之后：" + number);
        this.notifyAll();
    }

}

public class ThreadWaitNotify {
    public static void main(String[] args) {
        Zy zy = new Zy();
        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    zy.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程1");

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    zy.decrement();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程2");

        Thread t1_ = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    zy.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程1_");

        Thread t2_ = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    zy.decrement();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程2_");

        t1.start();
        t2.start();
        t1_.start();
        t2_.start();
    }

}
