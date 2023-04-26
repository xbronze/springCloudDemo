package com.springcloud.business.concurrency.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: xbronze
 * @date: 2023-04-24 14:25
 * @description: TODO
 */
@Data
public class TicketDaily {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private int totalTicket;

    private int reservedTicket;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activityDate;
}
