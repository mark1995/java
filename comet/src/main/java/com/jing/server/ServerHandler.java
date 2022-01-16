package com.jing.server;

import com.alibaba.fastjson.JSONObject;
import com.jing.dto.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.jing.dto.RequestFuture;

/**
 * @description: 业务处理handler
 * @author: GXK
 * @create: 2022-01-06 12:15
 **/

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestFuture requestFuture = JSONObject.parseObject(msg.toString(), RequestFuture.class);
        long id = requestFuture.getId();
        System.out.println("请求信息 为 ===" + msg.toString());

        Response response = new Response();
        response.setId(id);
        response.setResult("服务器响应ok");

        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
    }
}


