package com.nowcoder.community;

import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
// 使用 CommunityApplication 配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class ThreadPoolTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTests.class);

    // JDK 普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK 可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // Spring 普通线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // Spring 可执行定时任务的线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private AlphaService alphaService;

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // JDK 线程池
    @Test
    public void testExecutorService() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Hello ExecutorService.");
            }
        };

        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }
        sleep(10000);
    }

    // JDK 定时任务线程池
    @Test
    public void testScheduledExecutorService() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Hello ScheduledExecutorService.");
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(task, 10000, 1000, TimeUnit.MILLISECONDS);
        sleep(30000);
    }


    // Spring 普通线程池
    @Test
    public void testThreadPoolTaskExecutor() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Hello ThreadPoolTaskExecutor.");
            }
        };

        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.submit(task);
        }

        sleep(10000);
    }

    // Spring 定时任务线程池
    @Test
    public void testThreadPoolTaskScheduler() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Hello ThreadPoolTaskScheduler.");
            }
        };
        Date startTime = new Date(System.currentTimeMillis() + 10000);
        threadPoolTaskScheduler.scheduleAtFixedRate(task, startTime, 1000);

        sleep(30000);
    }

    // Spring 普通线程池（简化）
    @Test
    public void testThreadPoolTaskExecutorSimple() {
        for (int i = 0; i < 10; i++) {
            alphaService.execute1();
        }
        sleep(10000);
    }

    // Spring 定时任务线程池（简化）
    @Test
    public void testThreadPoolTaskSchedulerSimple() {
        sleep(30000);
    }
}