package com.jing.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 主线程类
 * @author: GXK
 * @create: 2022-01-07 16:33
 **/

public class FutureMain {



    public static void main(String[] args) {
          List<RequestFuture> reqs = new ArrayList<>();
          for (int i = 1; i < 100; i++) {
              long id = i;
              RequestFuture req = new RequestFuture();
              req.setId(id);
              req.setRequest("hello world " + i);
              RequestFuture.addFuture(req);
              reqs.add(req);

              sendMsg(req);

              SubThread subThread = new SubThread(req);
              subThread.start();
          }

          for (RequestFuture requestFuture : reqs) {
              Object result = requestFuture.get();
              System.out.println(result.toString());
          }

    }

    private static void sendMsg(RequestFuture requestFuture) {
        System.out.println("客户端 发送 数据， 请求id为 =====" + requestFuture.getId());
    }
}


