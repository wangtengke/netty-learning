package com.wtk.netty.nettyclient.handler;

import com.wtk.netty.nettycoder.Job;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @program: netty
 * @description:
 * @author: WangTengKe
 * @create: 2018-11-11
 **/
@Slf4j
public class TimeClientHandler extends SimpleChannelInboundHandler<Job> {

    private static final int TRY_TIMES = 3;

    private int currentTime = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Job msg) throws Exception {
//        ctx.writeAndFlush(unixTime);
//        ByteBuf in = (ByteBuf) msg;
//        System.out.println("i am "+msg);
        log.info("i am {{}}",msg.toString());
        ctx.close();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx){
//        System.out.println("hello i am client  channelActive");
//        ChannelFuture f = ctx.writeAndFlush("yes");
//        f.addListener(ChannelFutureListener.CLOSE);
//        ctx.close();
        Job job = new Job();
        job.setJobid(1);
        job.setJobtype("move");
        final ChannelFuture f = ctx.writeAndFlush(job); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                System.out.println("finish1");
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("bye i am client channelInactive");
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("循环触发时间："+new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                if(currentTime <= TRY_TIMES){
                    System.out.println("currentTime:"+currentTime);
                    currentTime++;
                    ctx.writeAndFlush(evt);
                }
            }
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("complete");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
