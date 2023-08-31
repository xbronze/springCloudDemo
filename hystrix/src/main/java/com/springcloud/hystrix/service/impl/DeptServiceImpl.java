package com.springcloud.hystrix.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.hystrix.service.IDeptService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: xbronze
 * @date: 2023-05-05 14:50
 * @description: TODO
 */
@Service
public class DeptServiceImpl implements IDeptService {

    @Override
    public String deptInfoOk(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " deptInfoOk, id：" + id;
    }

    @HystrixCommand(fallbackMethod = "deptTimeoutHandler",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    @Override
    public String deptInfoTimeout(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " deptInfoTimeout, id：" + id;
    }

    public String deptTimeoutHandler(Integer id) {
        return "提醒：当前系统繁忙，请稍后再试！ 线程池：" + Thread.currentThread().getName()
                + " deptInfoTimeout, id：" + id;
    }
}
