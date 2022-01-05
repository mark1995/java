package com.jing.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: GXK
 * @create: 2022-01-05 17:11
 **/

public class CometInitializer extends ChannelInitializer<Channel> {

    private int readerIdleTimeSecond = 30;
    private int writeIdleTimeSecond = 0;
    private int allIdleTimeSecond = 0;

    public CometInitializer() {

    }

    public CometInitializer(int readerIdleTimeSecond) {
        this.readerIdleTimeSecond = readerIdleTimeSecond;
    }

    public CometInitializer(int readerIdleTimeSecond, int writeIdleTimeSecond, int allIdleTimeSecond) {
        this.readerIdleTimeSecond = readerIdleTimeSecond;
        this.writeIdleTimeSecond = writeIdleTimeSecond;
        this.allIdleTimeSecond = allIdleTimeSecond;
    }


    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new IdleStateHandler(this.readerIdleTimeSecond, this.writeIdleTimeSecond, this.allIdleTimeSecond));
    }
}


