package com.jing.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: GXK
 * @create: 2022-01-06 12:24
 **/

public class RequestFuture {

    public static Map<Long, RequestFuture> futures = new ConcurrentHashMap<Long, RequestFuture>();

    private long id;

    private Object request;

    private Object result;

    private long timeout = 5000;

    public static void addFuture(RequestFuture future) {
        futures.put(future.id, future);
    }

    /*
        同步获得响应结果
     */
    public Object get() {
        synchronized (this) {
            while (this.result == null) {
                try {
                    this.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.result;
    }

    /*
        异步线程 将结果返回主线程
     */
    public static void received(Response resp) {
        RequestFuture requestFuture = futures.remove(resp.getId());
        if (requestFuture != null) {
            requestFuture.setResult(resp.getResult());
            synchronized (requestFuture) {
                requestFuture.notify();
            }
        }


    }

    public static Map<Long, RequestFuture> getFutures() {
        return futures;
    }

    public static void setFutures(Map<Long, RequestFuture> futures) {
        RequestFuture.futures = futures;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}


