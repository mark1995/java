package com.jing.register.client;

import com.jing.register.common.HeartbeatRequest;
import com.jing.register.common.HeartbeatResponse;
import com.jing.register.common.RegisterRequest;
import com.jing.register.common.RegisterResponse;

/*
    负责发送http
 */
public class HttpSender {
    
    public RegisterResponse register(RegisterRequest registerRequest) {


        // 发送服务注册信息
        System.out.println("服务实例【"+ registerRequest + "】发起请求进行注册......");
        RegisterResponse response = new RegisterResponse();
        response.setCode(1);
        response.setStatus(RegisterResponse.SUCCESS);
        return response;

    }

    public HeartbeatResponse heartbeat(HeartbeatRequest request) {
        System.out.println("服务实例【" + request +"】发送心跳请求......");
        return new HeartbeatResponse();
    }
}
