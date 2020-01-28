package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.CommunityUtil;
import com.nowcoder.community.utils.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    // 上传文件的请求，必须使用 POST 方式
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片!");
            return "/site/setting";
        }

        // 对上传的文件进行一些必要的处理
        String fileName = headerImage.getOriginalFilename();

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "图片格式不正确!");
            return "/site/setting";
        }

        // 生成随机的文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        // 指定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            LOGGER.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!" + e);
        }

        // 更新当前用户头像的 web 访问路径
        // http:localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    // 向浏览器响应图片
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 先找到文件存放的位置
        fileName = uploadPath + "/" + fileName;
        // 得到文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片，用到字节流
        response.setContentType("image/" + suffix);
        try (   // JDK 7 自动关闭流
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            // 读数据
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            LOGGER.error("读取头像失败!" + e.getMessage());
        }
    }

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.POST)
    public String updatePasswordHeader(@RequestParam("password") String password,
                                       @RequestParam("newPassword") String newPassword, Model model) {
        User user = hostHolder.getUser();
        String oldPassword = CommunityUtil.md5(password + user.getSalt());
        if (!oldPassword.equals(user.getPassword())) {
            model.addAttribute("passwordMsg", "原密码错误,请重新输入!");
            return "/site/setting";
        } else {
            userService.updatePassword(user.getId(), CommunityUtil.md5(newPassword + user.getSalt()));
            model.addAttribute("passwordMsg", "修改密码成功!");
            return "/site/setting";
        }
    }
}