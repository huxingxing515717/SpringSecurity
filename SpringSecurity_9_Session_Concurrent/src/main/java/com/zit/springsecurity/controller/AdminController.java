package com.zit.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @GetMapping("hello")
    public String hello(){
        return "hello admin";
    }
}
