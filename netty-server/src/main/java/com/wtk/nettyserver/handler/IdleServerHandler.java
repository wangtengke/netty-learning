package com.wtk.nettyserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty
 * @description: 空闲事件触发
 * @author: WangTengKe
 * @create: 2018-11-19
 **/
@Slf4j
public class IdleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("heartbeat triggered");
    }
}
