package com.zit.springsecurity.kaptcha;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerificationCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 非登录请求不校验验证码
        if(!"/auth/form".equals(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else {
            try{
                verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }catch(VerificationCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse,e);
            }
        }// else
    }

    // 验证码校验
    public void verificationCode(HttpServletRequest httpServletRequest) throws VerificationCodeException{
        String requestCode = httpServletRequest.getParameter("kaptcha");
        HttpSession session = httpServletRequest.getSession();
        String savedCode = (String) session.getAttribute("kaptcha");
        if(!StringUtils.isEmpty(savedCode)){
            // 随手清除验证码，无论是失败，还是成功。客户端应在登录失败时刷新验证码
            session.removeAttribute("kaptcha");
        }

        // 校验不通过抛出异常
        if(StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(savedCode) ||
                !requestCode.equals(savedCode)){
            throw new VerificationCodeException();
        }// if
    }
}