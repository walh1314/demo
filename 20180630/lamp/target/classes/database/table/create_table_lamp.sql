USE `lamp`;

-- drop table  `sys_user`;
--  用户表

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `name` varchar(32) NOT NULL COMMENT '用户名，唯一',
  `zh_name` varchar(64) COMMENT '中文名',
  `en_name` varchar(64) COMMENT '英文名',
  `desc` varchar(512) COMMENT '描述',
  `email` varchar(128) COMMENT '邮箱',
  `password` varchar(128) NOT NULL COMMENT '邮箱',
  `age` bigint(4) COMMENT '年龄',
  `mobile` varchar(64) COMMENT '手机号码',
  `status` bigint(4) COMMENT '用户状态（-1：禁用，1：正常）',
  `telephone` varchar(64) COMMENT '电话号码',
  `short_phone` varchar(16) COMMENT '短号',
  `employee_id` varchar(32) COMMENT '员工工号',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `modifier` varchar(32) NOT NULL COMMENT '修改人，默认为创建人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_un` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';


-- DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `code` varchar(64) NOT NULL COMMENT 'Code,唯一',
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_permission_code_un` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- DROP TABLE IF EXISTS `sys_permission_init`;
CREATE TABLE `sys_permission_init` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `permission_init` varchar(255) DEFAULT NULL COMMENT '需要具备的权限',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限初始化表';

-- DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `code` varchar(64) NOT NULL COMMENT 'Code,唯一',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `rid` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `pid` varchar(64) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `uid` varchar(64) DEFAULT NULL COMMENT '用户名',
  `rid` varchar(64) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户角色表';


-- drop table  `lamp_type`;
--  灯类型表
CREATE TABLE `lamp_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `code` varchar(32) NOT NULL COMMENT '类型code，唯一',
  `name` varchar(32) NOT NULL COMMENT '类型名',
  `order` int(11) COMMENT '排序,默认按照自增',
  `desc` varchar(512) COMMENT '描述',
  `status` bigint(4) COMMENT '状态（1：有效，-1：无效，默认为1）',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `modifier` varchar(32) NOT NULL COMMENT '修改人，默认为创建人',
  `modify_time` NOT NULL datetime COMMENT '修改时间',
  `create_time` NOT NULL datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_type_code_un` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯类型表';


-- drop table  `lamp_info`;
--  灯信息
CREATE TABLE `lamp_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `name` varchar(32) COMMENT '灯名',
  `serial` varchar(128) COMMENT '序列号，唯一',
  `machine_num` varchar(128) COMMENT '机器编号',
  `maintainer` varchar(32) COMMENT '维护人',
  `type` varchar(32) NOT NULL COMMENT '灯类型',
  `status` bigint(4) NOT NULL COMMENT '状态（1：有效，-1：无效，默认为1）',
  `desc` varchar(512) COMMENT '描述',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `modifier` varchar(32) NOT NULL COMMENT '修改人，默认为创建人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lamp_info_serial_un` (`serial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灯信息表';


-- drop table  `camera_type`;
--  camera类型表
CREATE TABLE `camera_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `code` varchar(32) NOT NULL COMMENT '类型code，唯一',
  `name` varchar(32) NOT NULL COMMENT '类型名',
  `order` int(11) COMMENT '排序,默认按照自增',
  `desc` varchar(512) COMMENT '描述',
  `status` bigint(4) NOT NULL COMMENT '状态（1：有效，-1：无效，默认为1）',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `modifier` varchar(32) NOT NULL COMMENT '修改人，默认为创建人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `camera_type_code_un` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摄影机类型表';


-- drop table  `camera_info`;
--  摄影机信息
CREATE TABLE `camera_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `name` varchar(32) COMMENT '名称',
  `serial` varchar(128) COMMENT '序列号，唯一',
  `device_id` varchar(128) COMMENT '设备id，唯一',
  `mac_addr` varchar(32) COMMENT 'mac地址，唯一',
  `pub_net_addr` varchar(16) COMMENT '公网地址',
  `lan_net_addr` varchar(16) COMMENT '局域网地址',
  `type` varchar(32) NOT NULL COMMENT '摄影机类型',
  `location_addr` varchar(512) COMMENT '地理位置',
  `status` bigint(4) COMMENT '状态（1：有效，-1：无效，默认为1）',
  `desc` varchar(512) COMMENT '描述',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `modifier` varchar(32) NOT NULL COMMENT '修改人，默认为创建人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `camera_info_device_id_un` (`device_id`),
  UNIQUE KEY `camera_info_serial_un` (`serial`),
  UNIQUE KEY `camera_info_mac_addr_un` (`mac_addr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摄影机信息表';

-- drop table  `camera_lamp_real`;
--  摄影机和灯关系表
CREATE TABLE `camera_lamp_real` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `device_id` varchar(32) NOT NULL COMMENT '摄影机设备id',
  `lamp_serial` varchar(128) NOT NULL  COMMENT '灯序列号',
  `points` json COMMENT '灯和摄影机的坐标关系，保存为json对象',
  `status` bigint(4) COMMENT '状态（1：有效，-1：无效，默认为1）',
  `desc` varchar(512) COMMENT '描述',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `modifier` varchar(32) NOT NULL COMMENT '修改人，默认为创建人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摄影机和灯关系表';