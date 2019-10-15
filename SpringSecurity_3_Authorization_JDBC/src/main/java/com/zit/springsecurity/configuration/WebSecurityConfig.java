package com.zit.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    @Qualifier("myDataSource")
    private DataSource myDataSource;

    /**
     * 引入多用户
     */
    @Bean
    public UserDetailsService userDetailsService(){
        // 用户信息保存在内存中
        /*InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("123").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("123").roles("USER","ADMIN").build());
        return manager;*/
        // 用户信息保存在数据库中
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(myDataSource);
        if(!manager.userExists("user")){// 判断是否存在
            manager.createUser(User.withUsername("user").password("123").roles("USER").build());// 保存到数据库中
        }
        if(!manager.userExists("admin")){
            manager.createUser(User.withUsername("admin").password("123").roles("USER","ADMIN").build());
        }
        return manager;
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }
}
