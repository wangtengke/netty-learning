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
        log.info("server print{{}}", msg.toString());
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
        System.out.println("server channelInactive");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("idle event triggered!");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectTime++;
                System.out.println("5 秒没有接收到客户端的信息了");
                if (lossConnectTime > 2) {
                    System.out.println("关闭这个不活跃的channel");
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
        cause.printStackTrace();
        ctx.close();
    }
}
