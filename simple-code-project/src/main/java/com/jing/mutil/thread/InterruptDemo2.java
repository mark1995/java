package com.jing.mutil.thread;

import lombok.Data;

public class InterruptDemo2 {


    // 核心的工作线程，shutdown 优雅的关闭，
    // 设置一个是否运行的标志位
    public static void main(String[] args) throws Exception {
        MyThread thread = new MyThread();
        thread.start();
        Thread.sleep(1000L);
        thread.setShouldRun(false);
        thread.interrupt();
    }

    @Data
    static class MyThread extends Thread {
        private boolean shouldRun = true;

        @Override
        public void run() {
            while (shouldRun) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is working");
                    Thread.sleep(30 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
