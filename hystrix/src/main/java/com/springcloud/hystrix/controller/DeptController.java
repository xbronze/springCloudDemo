package com.springcloud.hystrix.controller;

import com.springcloud.hystrix.service.IDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xbronze
 * @date: 2023-05-05 15:37
 * @description: TODO
 */
@RestController
@Slf4j
@RequestMapping("/hystrix")
public class DeptController {

    private final IDeptService deptService;

    public DeptController(IDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/ok/{id}")
    public String ok(@PathVariable("id") Integer id){
        return deptService.deptInfoOk(id);
    }

    @GetMapping("/timeout/{id}")
    public String timeout(@PathVariable("id") Integer id) {
        return deptService.deptInfoTimeout(id);
    }
}
