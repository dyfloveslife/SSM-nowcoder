package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
@Deprecated // 表示不推荐使用当前的接口
public interface LoginTicketMapper {

    // 字符串之间可以拼接，注意每句话后面的空格
    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired) ",
            "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!= null\"> ",
            "and 1=1 ",
            "</if> ",
            "</script>"
    })
    // 修改凭证的状态
    int updateStatus(String ticket, int status);
}
