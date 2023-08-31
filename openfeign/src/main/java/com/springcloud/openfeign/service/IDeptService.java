package com.springcloud.openfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: xbronze
 * @date: 2023-05-05 16:39
 * @description: TODO
 */
@Component  // 添加为容器内的一个组件
@FeignClient(value = "SPRING-CLOUD-BUSINESS") // 服务提供者提供的服务名称，即spring.application.name
public interface IDeptService {

    /**
     * 对应服务提供者中定义的方法
     * @return
     */
    @GetMapping(value = "/business/sayhi")
    String get();

}
