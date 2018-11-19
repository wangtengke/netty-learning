package com.wtk.netty.nettyclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

/**
 * @program: netty
 * @description: 客户端启动
 * @author: WangTengKe
 * @create: 2018-11-19
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.wtk.netty"})
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
