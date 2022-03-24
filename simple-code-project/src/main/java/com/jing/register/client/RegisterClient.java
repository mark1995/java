package com.jing.register.client;


/*
    register client 
 */
public class RegisterClient {

    private String serviceInstanceId;


    public void start() {
        // 1. 开启线程负责注册
        RegisterWorker registerWorker = new RegisterWorker(new HttpSender(), serviceInstanceId);
        registerWorker.start();
    }
}
