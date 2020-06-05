package com.cn.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 解决安全问题的替换方法
 * 1.vector
 * 2.synchronizedList(new ArrayList)
 * 3.CopyOnWriteArrayList
 * 4.
 */
public class NoSafe {
    public static void main(String[] args) throws Exception {

        List<String> list = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(list);
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(list);
        executorService.shutdown();
    }
}
