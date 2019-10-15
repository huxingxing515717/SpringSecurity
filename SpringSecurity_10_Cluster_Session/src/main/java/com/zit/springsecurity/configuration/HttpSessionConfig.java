package com.zit.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 为Spring Security提供集群支持的会话注册表
 */
@EnableRedisHttpSession
public class HttpSessionConfig {

    // 提供Redis连接，默认是localhost:6379
    @Bean
    public RedisConnectionFactory connectionFactory(){
        return new JedisConnectionFactory();
    }

    // httpSession的事件监听，改用session提供的会话注册表
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }
}
