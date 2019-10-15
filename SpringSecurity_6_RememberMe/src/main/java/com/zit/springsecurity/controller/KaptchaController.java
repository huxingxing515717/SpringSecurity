package com.zit.springsecurity.controller;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

@Controller
public class KaptchaController {

    @Bean
    public Producer kaptcha(){
        // 配置图形验证码的基本参数
        Properties properties = new Properties();
        // 图片宽度
        properties.setProperty("kaptcha.image.width","150");
        // 图片高度
        properties.setProperty("kaptcha.image.height","50");
        // 字符集
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        // 字符长度
        properties.setProperty("kaptcha.textproducer.char.length","4");

        Config config = new Config(properties);
        // 使用默认的图形验证码实现，当然也可以自定义实现
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Autowired
    private Producer kaptchaProducer;

    @GetMapping("/kaptcha.jpg")
    public void getKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 设置内容类型
        response.setContentType("image/jpeg");
        // 创建验证码文本
        String kapText = kaptchaProducer.createText();
        // 将验证码文本设置到session
        request.getSession().setAttribute("kaptcha",kapText);
        // 创建验证码图片
        BufferedImage bi = kaptchaProducer.createImage(kapText);
        // 获取响应输出流
        ServletOutputStream out = response.getOutputStream();
        // 将图片验证码数据写到响应输出流
        ImageIO.write(bi, "jpg",out);
        // 推送并关闭响应输出流
        try{
            out.flush();
        }finally {
            out.close();
        }
    }
}
