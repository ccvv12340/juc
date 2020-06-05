package com.cn.threadwaitnotify;

/**
 * 多线程之间 ，按照顺序调用，实现 A->B->C 线程启动，要求如下：
 * <p>
 * AA打印5次 BB打印10次 CC打印15 次 。。。来10轮
 */
class Zy1 {
    private int number = 0;

    public synchronized void getA() throws Exception {
        while (number != 0) {
            this.wait();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("AA");
        }
        number++;
        System.out.println("A number"+number);
        this.notifyAll();
    }

    public synchronized void getB() throws Exception {
        while (number != 1) {
            this.wait();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("BB");
        }
        number++;
        System.out.println("B number"+number);
        this.notifyAll();
    }

    public synchronized void getC() throws Exception{
        while (number != 2) {
            this.wait();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("CC");
        }
        number = number-2;
        System.out.println("c number"+number);
        this.notifyAll();
    }
}

public class ThreadOrderAccess {
    public static void main(String[] args) {
        Zy1 zy = new Zy1();
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    zy.getA();
                    System.out.println("第"+i+"轮打印A");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"线程1");
        Thread t2 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    zy.getB();
                    System.out.println("第"+i+"轮打印B");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"线程2");
        Thread t3 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    zy.getC();
                    System.out.println("第"+i+"轮打印C");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"线程3");

        t1.start();
        t2.start();
        t3.start();
    }

}
