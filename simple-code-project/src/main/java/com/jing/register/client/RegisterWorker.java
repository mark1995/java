package com.jing.register.client;

import com.jing.register.common.HeartbeatRequest;
import com.jing.register.common.RegisterRequest;
import com.jing.register.common.RegisterResponse;

/*
    负责项register-worker 发起注册申请的线程
 */
public class RegisterWorker extends Thread {
    
    public static final String SERVICE_NAME = "inventory-service";
    public static final String IP = "192.168.2.102";
    public static final String HOSTNAME = "inventory01";
    public static final int PORT = 9000;
    
    // http 通讯组件
    private HttpSender httpSender;
    
    private boolean finishedRegister = false;
    
    private String serviceInstanceId;
    
    public RegisterWorker() {}
    
    
    public RegisterWorker(HttpSender httpSender, String serviceInstanceId) {
        this.httpSender = httpSender;
        this.finishedRegister = false;
        this.serviceInstanceId = serviceInstanceId;
    }

    @Override
    public void run() {
        // 1. 获取机器信息
        // 2. ip地址，hostname, 服务监听端口
        // 3. 从配置文件可以拿出来
        if (!finishedRegister){
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setServiceName(SERVICE_NAME);
            registerRequest.setHostName(HOSTNAME);
            registerRequest.setIp(IP);
            registerRequest.setPort(PORT);
            registerRequest.setServiceInstanceId(serviceInstanceId);
            RegisterResponse response = httpSender.register(registerRequest);
            if (response.getStatus().equals(RegisterResponse.SUCCESS)) {
                finishedRegister = true;
            } else {
                // 注册不成功，打印错误日志
                return;
            }
        }

        while (true) {
            HeartbeatRequest request = new HeartbeatRequest();
            httpSender.heartbeat(request);
            try {
                Thread.sleep(30 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
