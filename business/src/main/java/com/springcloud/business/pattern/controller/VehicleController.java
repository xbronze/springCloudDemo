package com.springcloud.business.pattern.controller;

import com.springcloud.business.pattern.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xbronze
 * @date: 2023-04-23 14:54
 * @description: TODO
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("/{type}")
    public String vehicle(@PathVariable("type") String type){
        return vehicleService.choose(type);
    }
}
