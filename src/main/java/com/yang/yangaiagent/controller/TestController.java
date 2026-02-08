package com.yang.yangaiagent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "hello world 启动成功啦～";
    }

    @GetMapping("/")
    public String home() {
        return "欢迎访问根路径";
    }
}
