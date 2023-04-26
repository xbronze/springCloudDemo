-- museum.ticket_daily

CREATE TABLE `ticket_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `total_ticket` int(11) NOT NULL COMMENT '总票数',
  `reserved_ticket` int(11) NOT NULL DEFAULT '0' COMMENT '已预订',
  `activity_date` date NOT NULL COMMENT '活动日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO museum.ticket_daily
(total_ticket, reserved_ticket, activity_date)
VALUES(100, 0, '2023-04-24');


-- museum.ticket_order

CREATE TABLE `ticket_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;