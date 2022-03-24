package com.jing.mutil.thread;

public class VolatileDemo {

    // volatile 解决cpu三级缓存中的2级缓存的不一致读取的问题

    // 1. 总线加锁机制，比较早的解决方案，就是加一个锁，串行化的性能问题，基本没有用到多级缓存
    // 2. MESI协议 （缓存一致性协议）
    // 3. cpu嗅探机制

    // 有序性
    // 原子性：原子性，会有一个覆盖写的问题，i++ => 1 另一个线程 i++ => 1 应该是2，但还是1，覆盖写的问题，需要保证是时序性，只能一个人修改




    // 多个线程 共用 同一个变量

    static int flag = 0;
    static volatile int volatileValue = 0;

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                int localFlag = flag;
                int localVolatileValue = volatileValue;
                while (true) {
                    if (localFlag != flag) {
                        System.out.println(Thread.currentThread().getName() + " known flag update" +
                                "old flag " + localFlag +
                                "new flag " + flag);
                        localFlag = flag;
                    }

                    if (volatileValue != localVolatileValue) {
                        System.out.println(Thread.currentThread().getName() + " known volatile value change " +
                                "old volatile " + localVolatileValue +
                                "new volatile " + volatileValue);
                        localVolatileValue = volatileValue;
                    }
                   
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = i++;
                    System.out.println(Thread.currentThread().getName() + "change flag value " + flag);
                    volatileValue = i++;
                    System.out.println(Thread.currentThread().getName() + " change volatile value " + volatileValue);
                }
            }
        }.start();
    }
}
