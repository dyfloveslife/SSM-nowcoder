package com.nowcoder.community;

import com.nowcoder.community.utils.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里可以赌博，嫖娼，可以开票，哈哈!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这里可以❤赌❤博❤，嫖❤娼❤，可以❤开❤票❤❤，哈哈!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
