package com.springcloud.business.concurrency.controller;

import com.springcloud.business.concurrency.service.ITicketService;
import com.springcloud.business.framework.annotation.LimitAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: xbronze
 * @date: 2023-04-24 09:37
 * @description: 门票预约
 */
@Api("门票秒杀控制类")
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final ITicketService ticketService;

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ApiOperation("预约")
    @PostMapping("/reserve")
    @LimitAccess(key = "reserve", permitsPerSecond = 2, timeout = 500)
    public String reserve(int userId){
        // 悲观锁，一次只有一个线程进入，其他线程都在阻塞
        synchronized (this) {
            return ticketService.reserve(userId);
        }
    }
}
