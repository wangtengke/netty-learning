package com.wtk.nettyclient.handler;


import com.wtk.nettyconfig.nettycoder.Job;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.util.Date;
import java.util.Random;

/**
 * @program: netty
 * @description:
 * @author: WangTengKe
 * @create: 2018-11-11
 **/
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<Job> {

    private static final int TRY_TIMES = 3;

    private int currentTime = 0;

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(new Random().nextInt(10) + "客户端心跳信息", CharsetUtil.UTF_8));

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Job msg) throws Exception {
//        ctx.writeAndFlush(unixTime);
//        ByteBuf in = (ByteBuf) msg;
//        System.out.println("i am "+msg);
        log.info("server read {{}}", msg.toString());
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        log.info("channelActive success!");
        Job job = new Job();
        job.setJobid(1);
        job.setJobtype("move");
        final ChannelFuture f = ctx.writeAndFlush(job); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                log.info("channelActive finish");
//                System.out.println("channelActive finish");
//                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive!");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("循环触发时间：{}",new Date());
//        System.out.println("循环触发时间：" + new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                if (currentTime <= TRY_TIMES) {
                    log.info("currentTime:{}",currentTime);
//                    System.out.println("currentTime:" + currentTime);
                    currentTime++;
                    ctx.writeAndFlush(new Job());
                }
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        log.info("client read complete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("client ERROR!");
        cause.printStackTrace();
        ctx.close();
    }

}
