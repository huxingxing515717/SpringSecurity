package com.zit.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Configuration
public class MyPasswordEncoder extends BCryptPasswordEncoder {

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(12);
        return bcryptPasswordEncoder;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String encode(CharSequence charSequence){
        return passwordEncoder.encode(charSequence.toString());
    }

    // BCrypt密文的正则不表达式
    private static Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Override
    public boolean matches(CharSequence rawPassword,String encodedPassword){
        // 如果密码不是BCryt密文
        if(!BCRYPT_PATTERN.matcher(encodedPassword).matches()){
            //其它加密方式->当前兼容未加密场景
            return encodedPassword.equals(rawPassword.toString());
        }

        // BCrypt加密场景
        return super.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        PasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(12);
        System.out.println(bcryptPasswordEncoder.encode("123"));
    }
}
