package com.springcloud.business.pattern.service.impl;

import com.springcloud.business.pattern.service.ICarService;
import com.springcloud.business.pattern.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xbronze
 * @date: 2023-04-23 15:17
 * @description: TODO
 */
@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private CarServiceContent carServiceContent;

    @Override
    public String choose(String type) {
        ICarService carService = carServiceContent.getCarService(type);
        return carService.run();
    }
}
