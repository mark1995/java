package com.jing.register.common;


import lombok.Data;

/*
    注册请求
 */
@Data
public class RegisterRequest {

    private String serviceName;

    private String ip;

    private String hostName;

    private int port;

    private String serviceInstanceId;

}
