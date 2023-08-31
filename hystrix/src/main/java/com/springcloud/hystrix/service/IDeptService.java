package com.springcloud.hystrix.service;

/**
 * @author: xbronze
 * @date: 2023-05-05 14:49
 * @description: TODO
 */
public interface IDeptService {

    /**
     * hystrix 熔断器示例 ok
     * @param id
     * @return
     */
    String deptInfoOk(Integer id);

    /**
     * hystrix 熔断器超时案例
     * @param id
     * @return
     */
    String deptInfoTimeout(Integer id);

}
