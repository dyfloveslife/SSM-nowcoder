package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CommentMapper {

    // 由于需要分页，所以这里需要用到两个方法
    // 一个是查询某页有多少数据，另一个是查询一共有多少条数据，用于计算总页数
    // 帖子的评论、评论的评论

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    // 增加评论
    int insertComment(Comment comment);
}
