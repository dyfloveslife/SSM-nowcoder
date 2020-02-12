package com.nowcoder.community.config;

import com.nowcoder.community.quartz.AlphaJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * 配置 -> 数据库 -> 调用
 */
@Configuration
public class QuartzConfig {

    // FactoryBean 可以封装 Bean 来简化 Bean 的实例化过程
    // 将 FactoryBean 装配到 Spring 容器里，可以注入给其它 Bean 使用
    // 其它 Bean 使用到的是 FactoryBean 所管理的对象实例

    // 配置 JobDetail
    // @Bean
    public JobDetailFactoryBean alphaJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    // 配置 Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)
    // @Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(alphaJobDetail);
        factoryBean.setName("alphaTrigger");
        factoryBean.setGroup("alphaTriggerGroup");
        factoryBean.setRepeatInterval(3000);
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }
}
