--任务服务器
CREATE TABLE `timedtask_server` (
  `id` tinyint(4) NOT NULL,
  `server_ip` varchar(15) NOT NULL DEFAULT '' COMMENT '服务器ip',
  `server_port` varchar(4) NOT NULL DEFAULT '' COMMENT '服务器端口',
  `isOn` char(1) NOT NULL DEFAULT '' COMMENT '服务器是否开启，1：开启，0：关闭',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--服务器启动的任务
CREATE TABLE `timedtask_server_task` (
  `id` tinyint(4) NOT NULL,
  `task_name` varchar(255) NOT NULL DEFAULT '' COMMENT '任务名',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `isOn` char(1) NOT NULL DEFAULT '' COMMENT '任务是否开启',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--任务详情
CREATE TABLE `timedtask_task_detail` (
  `id` tinyint(4) NOT NULL,
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `interval_time` int(11) NOT NULL COMMENT '循环等待执行时间',
  `isOn` char(1) NOT NULL DEFAULT '' COMMENT '任务是否开启',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
