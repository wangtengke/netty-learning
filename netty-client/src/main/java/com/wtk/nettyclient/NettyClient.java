package com.wtk.nettyclient;

import com.wtk.nettyclient.handler.NettyClientHandler;
import com.wtk.nettyconfig.nettycoder.JobDecoder;
import com.wtk.nettyconfig.nettycoder.JobEncoder;
import com.wtk.nettyconfig.nettyconfig.NettyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: netty
 * @description:
 * @author: WangTengKe
 * @create: 2018-11-11
 **/
@Component
@Slf4j
public class NettyClient {
    @Autowired
    private NettyConfig nettyConfig;

    public void start() {
        int port = nettyConfig.getPort();
        String address = nettyConfig.getAddress();
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
                                    .addLast(new NettyClientHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(address, port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
//            System.out.println("try finish");
        } catch (InterruptedException e) {
            log.error("client启动失败");
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();

        }
    }
}
