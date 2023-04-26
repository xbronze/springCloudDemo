package com.springcloud.business.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xbronze
 * @date: 2023-04-24 11:48
 * @description: TODO
 */
@Configuration
@MapperScan("com.springcloud.business.**.mapper")
public class ApplicationConfig {
}
