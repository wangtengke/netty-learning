package com.wtk.nettyserver.handler;


import com.wtk.nettyconfig.nettycoder.Job;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty
 * @description: Handles a server-side channel.
 * @author: WangTengKe
 * @create: 2018-11-11
 **/
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Job> {

    private int lossConnectTime = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Job msg) throws Exception {

//        ByteBuf in = (ByteBuf) msg;
//        System.out.println(msg.toString());
        log.info("server read print{{}}", msg.toString());
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        log.info("server channelActive success");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        log.info("server read complete");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("server channelInactive");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("idle event triggered!");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectTime++;
                log.info("4 秒没有接收到客户端的信息了");
//                System.out.println("5 秒没有接收到客户端的信息了");
                if (lossConnectTime > 2) {
                    log.info("关闭这个不活跃的channel");
//                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        log.error("server ERROR!");
        cause.printStackTrace();
        ctx.close();
    }
}
