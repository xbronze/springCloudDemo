package com.springcloud.business.concurrency.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springcloud.business.concurrency.domain.TicketDaily;
import com.springcloud.business.concurrency.domain.TicketOrder;
import com.springcloud.business.concurrency.mapper.TicketDailyMapper;
import com.springcloud.business.concurrency.mapper.TicketOrderMapper;
import com.springcloud.business.concurrency.service.ITicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: xbronze
 * @date: 2023-04-24 10:45
 * @description: TODO
 */
@Service
public class TicketServiceImpl implements ITicketService {

    private final TicketDailyMapper ticketDailyMapper;
    private final TicketOrderMapper ticketOrderMapper;

    public TicketServiceImpl(TicketDailyMapper ticketDailyMapper, TicketOrderMapper ticketOrderMapper) {
        this.ticketDailyMapper = ticketDailyMapper;
        this.ticketOrderMapper = ticketOrderMapper;
    }

    @Override
    public String reserve(int userId) {
        LambdaQueryWrapper<TicketDaily> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TicketDaily::getActivityDate, "2023-04-24");
        TicketDaily ticketDaily = ticketDailyMapper.selectOne(wrapper);
        if (ticketDaily.getReservedTicket() < ticketDaily.getTotalTicket()) {
            // 还有余票
            createOrder(userId, ticketDaily);
            return "预约成功";
        } else {
            System.out.println("无票");
            return "无票";
        }
     }

    @Transactional(rollbackFor = Exception.class)
    private void createOrder(int userId, TicketDaily ticketDaily) {
        // 剩余票数
        ticketDaily.setReservedTicket(ticketDaily.getReservedTicket() + 1);
        ticketDailyMapper.updateById(ticketDaily);
        // 创建订单
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setUserId(userId);
        ticketOrder.setCreateTime(new DateTime());
        ticketOrderMapper.insert(ticketOrder);
    }

}
