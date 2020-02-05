package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MessageMapper {

    // 由于需要用到分页，所以需要用到查询数据的方法，查询总行数的方法
    // 点进私信，也是需要查询数据以及总行数的方法
    // 还需要查询未读消息数量

    // 查询当前用户的会话列表，针对每个会话只返回一条最新的私信
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的会话数量
    int selectConversationCount(int userId);

    // 查询某个会话所包含的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // 查询某个会话所包含的私信数量
    int selectLetterCount(String conversationId);

    // 查询未读消息数量，动态的拼接 conversationId
    int selectLetterUnreadCount(int userId, String conversationId);

    // 发送私信，新增消息
    int insertMessage(Message message);

    // 未读消息变为已读消息
    int updateStatus(List<Integer> ids, int status);

    // 查询某个主题下最新的通知
    Message selectLatestNotice(int userId, String topic);

    // 查询某个主题所包含的通知的数量
    int selectNoticeCount(int userId, String topic);

    // 未读的通知数量
    int selectNoticeUnreadCount(int userId, String topic);

}
