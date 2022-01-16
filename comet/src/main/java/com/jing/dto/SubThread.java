package com.jing.dto;

/**
 * @description:
 * @author: GXK
 * @create: 2022-01-07 09:21
 **/

public class SubThread extends Thread {

    private RequestFuture requestFuture;

    public SubThread(RequestFuture requestFuture) {
        this.requestFuture = requestFuture;
    }

    @Override
    public void run() {
        Response response = new Response();

        response.setId(requestFuture.getId());

        response.setResult("Server response " + Thread.currentThread().getId());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RequestFuture.received(response);

    }
}


