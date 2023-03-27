package com.springcloud.eureka.test;

import lombok.Data;

/**
 * @author: xbronze
 * @date: 2023-03-23 16:40
 * @description: TODO
 */
@Data
public class User {

    private String name;
    private String sex;
    private Integer ages;

    public User(String name, String sex, Integer ages) {
        this.name = name;
        this.sex = sex;
        this.ages = ages;
    }
}
