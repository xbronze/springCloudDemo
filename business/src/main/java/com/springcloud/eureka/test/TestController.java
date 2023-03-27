package com.springcloud.eureka.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author: xbronze
 * @date: 2023-03-23 16:39
 * @description: TODO
 */
public class TestController {

    public static void main(String[] args) {
        User user1 = new User("zhangsan", "man", 1);
        User user2 = new User("zhangsan", "man", 2);
        User user3 = new User("lisi", "woman", 3);
        User user4 = new User("wanger", "man", 4);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);


        userList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> p.getName()+";"+p.getSex())))
                        , ArrayList::new)
        ).forEach(System.out::println);



    }

}
