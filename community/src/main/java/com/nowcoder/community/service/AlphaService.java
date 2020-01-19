package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
// 创建多个实例
//@Scope("prototype")
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;


    public AlphaService() {
        System.out.println("实例化 AlphaService...");
    }

    // 在构造器之后初始化
    @PostConstruct
    public void init() {
        System.out.println("初始化 AlphaService...");
    }

    // 在销毁对象之前，调用该方法
    @PreDestroy
    public void destory() {
        System.out.println("销毁 AlphaService...");
    }


    public String find() {
        return alphaDao.selcet();
    }
}
