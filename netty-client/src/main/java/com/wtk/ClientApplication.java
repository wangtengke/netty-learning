package com.wtk;

import com.wtk.nettyclient.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: netty
 * @description: 客户端启动
 * @author: WangTengKe
 * @create: 2018-11-19
 **/
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {
    @Autowired
    private NettyClient nettyClient;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyClient.start();
    }
}
