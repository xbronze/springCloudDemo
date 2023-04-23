package com.springcloud.business.pattern.service.impl;

import com.springcloud.business.pattern.service.ICarService;
import org.springframework.stereotype.Service;

/**
 * @author: xbronze
 * @date: 2023-04-23 14:50
 * @description: TODO
 */
@Service
public class BusServiceImpl implements ICarService {
    @Override
    public String run() {
        return "大巴车一般要求时速控制在每小时80公里";
    }
}
