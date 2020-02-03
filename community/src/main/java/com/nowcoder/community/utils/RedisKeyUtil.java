package com.nowcoder.community.utils;

/**
 * 统一管理 key
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    /**
     * 某个实体的赞
     * 采用 set(userId) 的目的是为了满足以后业务扩展的需要，
     * 假如需要获得点赞的具体用户，则可以通过 userId 获取
     * 格式：like:entity:entityType:entityId -> set(userId)
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
