package com.goldream.middleware.whitelist.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration 组件注解，在SpringBoot启动时可以进行加载创建出Bean文件 因为有一个@Component注解
@Configuration
//当 WhiteListProperties 位于当前类路径上，才会实例化一个类
@ConditionalOnClass(WhiteListProperties.class)
//启动自定的注解，使@ConfigurationProperties生效
@EnableConfigurationProperties(WhiteListProperties.class)
public class WhiteListAutoConfigure {
    @Bean("whiteListConfig") //properties配置会被注入进来
    @ConditionalOnMissingBean // 某个class类路径上不存在时，才会实例化一个Bean
    public String whiteListConfig(WhiteListProperties properties){
        return properties.getUsers();
    }
}
