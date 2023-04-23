package com.springcloud.business.pattern.service.impl;

import com.springcloud.business.pattern.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: xbronze
 * @date: 2023-04-23 15:07
 * @description: TODO
 */
@Service
public class CarServiceContent {

    @Autowired
    private Map<String, ICarService> carServiceMap;

    public ICarService getCarService(String type) {
        if (carServiceMap.isEmpty()) {
            return null;
        }
        return this.carServiceMap.get(type);
    }
}
