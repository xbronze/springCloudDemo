package com.springcloud.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xbronze
 * @date: 2023-03-30 17:32
 * @description: business模块controller测试类
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @GetMapping("/sayhi")
    public String sayHello() {
        System.out.println("hello world, iam business!");
        return "hello world, iam business!";
    }
}
