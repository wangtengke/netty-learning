package com.wtk.nettyconfig.nettyconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
