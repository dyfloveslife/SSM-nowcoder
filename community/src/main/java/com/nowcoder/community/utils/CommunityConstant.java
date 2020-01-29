package com.nowcoder.community.utils;

public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * "不记住我"的情况下，默认状态的登录凭证超时时间
     * 12 小时
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * "记住我"的情况下凭证超时时间
     */
    int REMEMBERME_EXPIRED_SECONDS = 3600 * 24 * 100;

}