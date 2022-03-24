package com.jing.register.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterResponse implements Serializable {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    private int code;
    private String status;
}
