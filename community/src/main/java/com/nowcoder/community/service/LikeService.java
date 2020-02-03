package com.nowcoder.community.service;

import com.nowcoder.community.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞功能
     *
     * @param userId
     * @param entityType
     * @param entityId
     */
    public void like(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        // 判断点赞次数
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        // 如果已经点过赞了，则需要将 userId 从 set 中删除掉
        if (isMember) {
            redisTemplate.opsForSet().remove(entityLikeKey, userId);
        } else {
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }
    }

    /**
     * 统计点赞数量
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 统计是否点过赞，即点赞状态
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return 返回整数，以便于对增加“踩”功能时的扩展
     */
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }
}
