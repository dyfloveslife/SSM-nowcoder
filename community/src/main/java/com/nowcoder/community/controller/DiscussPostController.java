package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.utils.CommunityUtil;
import com.nowcoder.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody  // 返回的是字符串
    // 浏览器给提供的只有 title 和 content
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        // 如果用户没有登录的话，则返回给页面一个 json 格式的数据
        // 403 表示没有权限，即服务器已经理解请求，但是拒绝执行它。
        if (user == null) {
            return CommunityUtil.getJSONString(403, "还没有登陆!");
        }

        // 开始生成帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);
        // 报错情况，将来统一处理
        return CommunityUtil.getJSONString(0, "发送成功!");
    }
}
