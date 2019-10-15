package com.zit.springsecurity.configuration;

import com.zit.springsecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //RedisOperationsSessionRepository
    @Autowired
    private FindByIndexNameSessionRepository mySessionRepository;

    // 是session为Spring Security提供的
    // 用于在集群环境下控制会话并发的会话注册表实现
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(mySessionRepository);
    }

    // 将新的会话注册表提供给Spring Security
    @Autowired
    private SpringSessionBackedSessionRegistry redisSessionRegistry;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .sessionManagement()
                // 最大会话数设置
                .maximumSessions(1)
                // 阻止新会话登录
                .maxSessionsPreventsLogin(true)
                // 使用session提供的会话注册表
                .sessionRegistry(redisSessionRegistry);
    }
}
