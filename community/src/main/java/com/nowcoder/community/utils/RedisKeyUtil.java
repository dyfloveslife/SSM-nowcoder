package com.nowcoder.community.utils;

/**
 * 统一管理构造 key
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    // 被关注者：明星
    private static final String PREFIX_FOLLOWEE = "followee";
    // 关注着：粉丝
    private static final String PREFIX_FOLLOWER = "follower";

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

    /**
     * 某个用户的赞
     * 格式：like:user:userId -> int
     *
     * @param userId
     * @return
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户关注的实体
     * 格式：followee:userId:entityType -> zset(entityId, nowTime)
     *
     * @param userId
     * @param entityType
     * @return
     */
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }


    /**
     * 某个实体拥有的粉丝
     * 格式：follower:entityType:entityId -> zset(userId, nowTime)
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }
}
