CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `info` varchar(500) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `password_reset_token` varchar(64),
  `reset_token_expires` datetime,
  `age` bigint(4) ,
  `last_sign_in_at` datetime,
   `status` bigint(4) ,
  `email` varchar(256) NOT NULL,
  `mobile` varchar(64),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
