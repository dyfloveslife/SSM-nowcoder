package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

// 给当前 bean ，即 AlphaDaoImpl 重新命名
@Repository("alphaImpl")
public class AlphaDaoImpl implements AlphaDao {
    @Override
    public String selcet() {
        return "AlphaDaoImpl...";
    }
}
