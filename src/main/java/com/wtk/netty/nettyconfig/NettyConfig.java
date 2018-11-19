package com.wtk.netty.nettyconfig;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @program: netty
 * @description: netty配置
 * @author: WangTengKe
 * @create: 2018-11-19
 **/
@Service
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    private int port;
    private String address;
}
