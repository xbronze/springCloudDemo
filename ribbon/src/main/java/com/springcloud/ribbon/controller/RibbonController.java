package com.springcloud.ribbon.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: xbronze
 * @date: 2023-03-30 17:46
 * @description: ribbon模块controller测试类
 */
@RestController
@RequestMapping("/ribbon")
@AllArgsConstructor
public class RibbonController {

    /**
     * RestTemplate 是一种简单便捷的访问 restful 服务模板类，是 Spring 提供的用于访问 Rest 服务的客户端模板工具集，提供了多种便捷访问远程 HTTP 服务的方法
      */
    private final RestTemplate restTemplate;

    /**
     * 面向微服务编程，即通过微服务的名称来获取调用地址
     * 用注册到 Spring Cloud Eureka 服务注册中心中的服务，即 application.name
     */
    private static final String BUSINESSSERVICEPROVIDER = "http://SPRING-CLOUD-BUSINESS";

    @GetMapping("/sayhi")
    public String sayHello() {
        return restTemplate.getForObject(BUSINESSSERVICEPROVIDER + "/business/sayhi", String.class);
    }

}
