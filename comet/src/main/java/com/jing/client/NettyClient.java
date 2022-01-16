package com.jing.client;

import com.alibaba.fastjson.JSONObject;
import com.jing.dto.RequestFuture;
import com.jing.dto.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;


/**
 * @description: netty客户端
 * @author: GXK
 * @create: 2022-01-07 16:54
 **/

public class NettyClient {

    public static EventLoopGroup group = null;
    public static Bootstrap bootstrap = null;


    static {
        bootstrap = new Bootstrap();

        group = new NioEventLoopGroup();

        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        // 设置内存分配器
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }

    public static void main(String[] args) {

        try {
            Promise<Response> promise = new DefaultPromise<>(group.next());


            final ClientHandler handler = new ClientHandler();

            handler.setPromise(promise);

            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    nioSocketChannel.pipeline()
                            .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                    0, 4, 0, 4));

                    nioSocketChannel.pipeline().addLast(new StringDecoder());
                    nioSocketChannel.pipeline().addLast(handler);
                    nioSocketChannel.pipeline().addLast(new LengthFieldPrepender(4, false));
                    nioSocketChannel.pipeline().addLast(new StringEncoder(Charset.forName("utf-8")));
                }
            });

            // 连接服务器
            ChannelFuture future = bootstrap.connect("127.0.0.1", 9091).sync();
            RequestFuture requestFuture = new RequestFuture();

            requestFuture.setId(1);
            requestFuture.setRequest("hello world");

            String requestStr = JSONObject.toJSONString(requestFuture);
            future.channel().writeAndFlush(requestStr);

            Response response = promise.get();
            System.out.println(JSONObject.toJSONString(response));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}


