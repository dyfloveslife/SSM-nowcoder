package com.nowcoder.community.utils;

/**
 * 统一管理构造 key
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWEE = "followee";  // 被关注者：明星
    private static final String PREFIX_FOLLOWER = "follower";  // 关注者：粉丝
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "USER";
    private static final String PREFIX_UV = "uv";
    private static final String PREFIX_DAU = "dau";
    private static final String PREFIX_POST = "post";

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


    /**
     * 登录的验证码
     * 这里不能传入 userId，因为刚开始登录的时候还不知道用户的 id 是多少
     *
     * @param owner
     * @return
     */
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    /**
     * 登录凭证
     *
     * @param ticket
     * @return
     */
    public static String getTicket(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    /**
     * 根据 userId 获取 User 对象
     *
     * @param userId
     * @return
     */
    public static String getUser(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

    /**
     * 单日的 UV
     *
     * @param date
     * @return
     */
    public static String getUVKey(String date) {
        return PREFIX_UV + SPLIT + date;
    }

    /**
     * 区间内的 UV
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getUVKey(String startDate, String endDate) {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }

    /**
     * 单日活跃用户
     *
     * @param date
     * @return
     */
    public static String getDAUKey(String date) {
        return PREFIX_DAU + SPLIT + date;
    }

    /**
     * 区间活跃用户
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getDAUKey(String startDate, String endDate) {
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }

    /**
     * 返回帖子分数
     *
     * @return
     */
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }
}
