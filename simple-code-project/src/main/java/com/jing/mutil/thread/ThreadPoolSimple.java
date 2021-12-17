package com.jing.mutil.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @description: 线程池
 * @author: GXK
 * @create: 2021-12-17 18:55
 **/

public class ThreadPoolSimple {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolSimple poolSimple = new ThreadPoolSimple();

        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        }, new ThreadPoolExecutor.AbortPolicy());
        poolSimple.printStats(threadPoolExecutor);

        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            try {
                threadPoolExecutor.submit(() -> {
                    System.out.println(id + " started");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(id + " finished");
                });
            } catch (Exception e) {
                atomicInteger.decrementAndGet();
            }
        });

        TimeUnit.SECONDS.sleep(60);
        System.out.println(atomicInteger.intValue());
    }

    public void printStats(ThreadPoolExecutor threadPoolExecutor) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("==================");
            System.out.println("Pool Size "+ threadPoolExecutor.getPoolSize());
            System.out.println("Active Threads "+ threadPoolExecutor.getActiveCount());
            System.out.println("Number of Tasks Completed " + threadPoolExecutor.getCompletedTaskCount());
            System.out.println("Number of Tasks in Queue "+ threadPoolExecutor.getQueue().size());
            System.out.println("===================");
        },0, 1, TimeUnit.SECONDS);
    }


}


