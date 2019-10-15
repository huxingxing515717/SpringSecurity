package com.zit.springsecurity.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin().loginPage("/myLogin.html")
                .loginProcessingUrl("/login")
                // 指定成功时的处理逻辑
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error_code\":\"0\",\"message\":\"欢迎登录系统！"+authentication.getName()+"\"}");
                    }
                })
                // 指定登录失败时的处理逻辑
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.setStatus(401);
                        PrintWriter out = httpServletResponse.getWriter();
                        // 输出失败原因
                        out.write("{\"error_code\":\"401\",\"message\":\""+e.getMessage()+"\"}");
                    }
                })
                .permitAll()
                .and().csrf().disable();// 使登录页不设限访问
    }
}
