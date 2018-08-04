
USE mqtt;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `desc` varchar(500) DEFAULT NULL,
  `device_id` varchar(256) NOT NULL,
  `pub_net_addr` varchar(256) NOT NULL,
  `lan_net_addr` varchar(256) NOT NULL,
  `identifier` varchar(256) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `mode` varchar(64) DEFAULT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `update_user` varchar(64) DEFAULT NULL,
  `ex_factory_date` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` bigint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;