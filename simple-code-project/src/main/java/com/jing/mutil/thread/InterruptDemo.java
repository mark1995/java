package com.jing.mutil.thread;

public class InterruptDemo {


    // interrupt， 可以中断线程的执行
    // 打断一个线程的休眠
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " is working");
                }
            }
        };
        thread.start();
        System.out.println("start interrupt thread");
        thread.interrupt();
    }
}
