package com.springcloud.business.pattern.service.impl;

import com.springcloud.business.pattern.service.ICarService;
import org.springframework.stereotype.Service;

/**
 * @author: xbronze
 * @date: 2023-04-23 14:51
 * @description: TODO
 */
@Service
public class SuperCarServiceImpl implements ICarService {

    @Override
    public String run() {
        return "超跑的车速轻松能达到每小时200公里";
    }

}
