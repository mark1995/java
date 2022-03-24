package com.jing.mutil.thread;

public class ThreadTest {

    // 这里还和goroutine的实现方式还不太一样，golang的main 主goroutine 退出之后，进程直接结束了，
    // 但是java不会，java new出来一个线程之后，java进程并不会退出
    // 如果有非daemon线程（worker线程），jvm线程是不会退出的，
    // 如果只是有daemon线程，jvm线程，后台线程 都会退出的
    // thread.setDaemon(true);
    public static void main(String[] args) {
        System.out.println("im start");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " running");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        System.out.println(Thread.currentThread().getName());
        System.out.println("im out");
    }
}
