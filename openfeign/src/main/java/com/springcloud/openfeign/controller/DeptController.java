package com.springcloud.openfeign.controller;

import com.springcloud.openfeign.service.IDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xbronze
 * @date: 2023-05-05 16:48
 * @description: TODO
 */
@RestController
@RequestMapping("/openfeign")
public class DeptController {

    private final IDeptService deptService;

    public DeptController(IDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/get")
    public String get(){
        return deptService.get();
    }


}
