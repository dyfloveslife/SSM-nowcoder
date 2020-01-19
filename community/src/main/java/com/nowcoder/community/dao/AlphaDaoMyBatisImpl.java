package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
// 由于两个 bean 都实现了 AlphaDao 接口，所以执行程序的时候不知道先调用哪个bean
// 这时可以使用 Primary 优先装配该 bean
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao {
    @Override
    public String selcet() {
        return "MyBatis...";
    }
}
