package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommunityApplication {


    /**
     * 由于 Redis 和 Elasticsearch 底层都依赖 Netty，所以需要修改 es 部分配置
     * 解决 netty 启动冲突的问题
     * see Netty4Utils
     */
    @PostConstruct
    public void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {

        SpringApplication.run(CommunityApplication.class, args);
    }
}
