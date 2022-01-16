package com.jing.client;

import com.alibaba.fastjson.JSONObject;
import com.jing.dto.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Promise;

/**
 * @description:
 * @author: GXK
 * @create: 2022-01-07 16:58
 **/

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Promise<Response> promise;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Response response = JSONObject.parseObject(msg.toString(), Response.class);
        promise.setSuccess(response);
    }

    public Promise<Response> getPromise() {
        return promise;
    }

    public void setPromise(Promise<Response> promise) {
        this.promise = promise;
    }
}


