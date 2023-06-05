package com.goldream.middleware.whitelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties用于创建指定前缀的自定义配置信息，这样就在yml或者properties中读取到我们自己设定的配置信息
@ConfigurationProperties(prefix = "goldream.whitelist")
public class WhiteListProperties {
    private String users;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
