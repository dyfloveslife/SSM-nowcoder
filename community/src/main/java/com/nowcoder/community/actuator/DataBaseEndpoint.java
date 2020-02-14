package com.nowcoder.community.actuator;

import com.nowcoder.community.utils.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用于监控数据库的连接
 */
@Component
@Endpoint(id = "database") // 给端点取名
public class DataBaseEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseEndpoint.class);

    @Autowired
    private DataSource dataSource;

    /**
     * 通过 get 请求访问
     *
     * @return
     */
    @ReadOperation
    public String checkConnection() {
        try (
                Connection conn = dataSource.getConnection();
        ) {
            return CommunityUtil.getJSONString(0, "获取连接成功!");
        } catch (SQLException e) {
            LOGGER.error("获取连接失败!" + e.getMessage());
            return CommunityUtil.getJSONString(1, "获取连接失败!");
        }

    }
}
