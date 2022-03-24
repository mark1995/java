package com.jing.register.server;

public class ServiceAliveMonitor {

    private static final Long CHECK_ALIVE_INTERVAL = 5 * 1000L;
    private static final String CHECK_DAEMON_THREAD_NAME = "alive_monitor";

    public Daemon daemon;

    public ServiceAliveMonitor() {
        daemon = new Daemon();
    }


    public void start() {
        daemon.start();
        daemon.setDaemon(true);
        daemon.setName(CHECK_DAEMON_THREAD_NAME);
    }


    static class Daemon extends Thread {


        @Override
        public void run() {
            // 检测服务的心跳是否超时
            while (true) {
                try {



                    Thread.sleep(CHECK_ALIVE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
