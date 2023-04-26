package com.springcloud.business.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: xbronze
 * @date: 2023-04-24 17:51
 * @description: TODO
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包路径,控制器类包
                .apis(RequestHandlerSelectors.basePackage("com.springcloud.business.concurrency.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("XX平台API接口文档")
                // 创建人
                .contact(new Contact("xuzihui", "http://www.xuzihui.cn",
                        "xuzihui.cn@gmail.com"))
                // 版本号
                .version("1.0")
                // 描述
                .description("系统API描述")
                .build();
    }
}
