package com.wtk.netty.nettyclient;

import com.wtk.netty.nettyclient.handler.TimeClientHandler;
import com.wtk.netty.nettycoder.JobDecoder;
import com.wtk.netty.nettycoder.JobEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty
 * @description:
 * @author: WangTengKe
 * @create: 2018-11-11
 **/
public class TimeClent {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup) // (2)
             .channel(NioSocketChannel.class) // (3)
             .option(ChannelOption.SO_KEEPALIVE, true) // (4)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
//                    .addLast(new StringDecoder())
//                    .addLast(new StringEncoder())
//                    .addLast("decoder",new TimeDecoder())
//                    .addLast("encoder",new TimeEncoder())
                    .addLast(new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS))
                    .addLast(new JobDecoder())
                    .addLast(new JobEncoder())
                    .addLast(new TimeClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
//            System.out.println("try finish");
        } finally {
            workerGroup.shutdownGracefully();

        }
    }
}
