--任务服务器
CREATE TABLE `timedtask_server` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `server_ip` varchar(15) NOT NULL DEFAULT '' COMMENT '服务器ip',
  `server_port` varchar(4) NOT NULL DEFAULT '' COMMENT '服务器端口',
  `is_on` char(1) NOT NULL DEFAULT '' COMMENT '服务器是否开启，1：开启，0：关闭',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--服务器启动的任务
CREATE TABLE `timedtask_server_task` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `task_name` varchar(100) NOT NULL COMMENT '任务名',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--任务详情
CREATE TABLE `timedtask_task_detail` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL COMMENT '任务id',
  `task_type` varchar(100) NOT NULL,
  `interval_time` int(11) NOT NULL COMMENT '循环等待执行时间',
  `is_on` char(1) NOT NULL DEFAULT '' COMMENT '任务是否开启',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--任务属性
CREATE TABLE `timedtask_task_prop` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL,
  `prop_name` varchar(100) NOT NULL,
  `prop_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


