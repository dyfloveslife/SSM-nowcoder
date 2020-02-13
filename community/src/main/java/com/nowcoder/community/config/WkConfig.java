package com.nowcoder.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 在服务启动的时候，创建 wk-images 和 wk-pdfs 目录
 */
@Configuration
public class WkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WkConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init() {
        File file = new File(wkImageStorage);

        if (!file.exists()) {
            file.mkdir();
            LOGGER.info("创建 WK 图片目录: " + wkImageStorage);
        }
    }
}
