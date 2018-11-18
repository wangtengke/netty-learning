package com.wtk.netty.nettyserver.handler;


import com.wtk.netty.nettycoder.Job;
import io.netty.channel.*;
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
public class DiscardServerHandler extends SimpleChannelInboundHandler<Job> {

    private int loss_connect_time = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Job msg) throws Exception {

//        ByteBuf in = (ByteBuf) msg;
//        System.out.println(msg.toString());
        log.info("server print{{}}",msg.toString());
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
//        System.out.println("hello i am server channelActive");
//        ChannelFuture f = ctx.writeAndFlush("i am server");
//        final ByteBuf time = ctx.alloc().buffer(4); // (2)
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//        time.writeByte(51);
//        final ChannelFuture f = ctx.writeAndFlush("ok"); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                assert f == future;
//                System.out.println("finish");
//                ctx.close();
//            }
//        }); // (4)
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("complete");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("bye i am server channelInactive");
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                loss_connect_time++;
                System.out.println("5 秒没有接收到客户端的信息了");
                if (loss_connect_time > 2) {
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
