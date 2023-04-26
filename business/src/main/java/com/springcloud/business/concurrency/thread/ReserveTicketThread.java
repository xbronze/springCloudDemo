package com.springcloud.business.concurrency.thread;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springcloud.business.concurrency.domain.TicketDaily;
import com.springcloud.business.concurrency.mapper.TicketDailyMapper;

/**
 * @author: xbronze
 * @date: 2023-04-24 10:49
 * @description: TODO
 */
public class ReserveTicketThread implements Runnable {

    private final TicketDailyMapper ticketDailyMapper;

    public ReserveTicketThread(TicketDailyMapper ticketDailyMapper) {
        this.ticketDailyMapper = ticketDailyMapper;
    }

    @Override
    public void run() {
        synchronized (this) {
            LambdaQueryWrapper<TicketDaily> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TicketDaily::getActivityDate, "2023-04-24");
            TicketDaily ticketDaily = ticketDailyMapper.selectOne(queryWrapper);
            if (ticketDaily.getReservedTicket() < ticketDaily.getTotalTicket()) {
                int reserved = ticketDaily.getReservedTicket() + 1;
                int surplus = ticketDaily.getTotalTicket() - reserved;
                System.out.println(Thread.currentThread().getName() + "预定了一张票，还剩" + surplus + "张");
                TicketDaily updateTicketDaily = new TicketDaily();
                updateTicketDaily.setId(ticketDaily.getId());
                updateTicketDaily.setTotalTicket(ticketDaily.getTotalTicket());
                updateTicketDaily.setReservedTicket(reserved);
                ticketDailyMapper.updateById(updateTicketDaily);
            }
        }
    }
}
