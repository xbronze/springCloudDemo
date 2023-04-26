package com.springcloud.business.concurrency.domain;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: xbronze
 * @date: 2023-04-26 10:02
 * @description: TODO
 */
@Data
public class TicketOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private int userId;

    @TableField(fill = FieldFill.INSERT)
    private DateTime createTime;
}
