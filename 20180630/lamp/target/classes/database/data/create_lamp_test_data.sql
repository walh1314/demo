INSERT INTO `sys_permission_init` (`url`,`permission_init`,`sort`) VALUES ('/static/**', 'anon,kickout', 1);
INSERT INTO `sys_permission_init` (`url`,`permission_init`,`sort`) VALUES ('/login', 'anon,kickout', 2);
INSERT INTO `sys_permission_init` (`url`,`permission_init`,`sort`) VALUES ('/logout', 'logout,kickout', 3);
INSERT INTO `sys_permission_init` (`url`,`permission_init`,`sort`) VALUES ('/**', 'user,kickout', 10);
INSERT INTO `sys_permission_init` (`url`,`permission_init`,`sort`) VALUES ('/getGifCode', 'anon,kickout', 4);
INSERT INTO `sys_permission_init` (`url`,`permission_init`,`sort`) VALUES ('/kickout', 'anon', 5);
-- user data
INSERT INTO `lamp`.`sys_user`
(`name`,`zh_name`,`en_name`,`desc`,`email`,`password`,`age`,`mobile`,`status`,`telephone`,`short_phone`,`employee_id`,`creater`,`modifier`,`modify_time`,`create_time`)
VALUES
('name001','name001','name001','name001','name001@test.com','wPfQv64jQZc846+QzEtTQw==',11,'15200483846',1,null,null,'F1334999','name01','name01',now(),now());

INSERT INTO `lamp`.`sys_user`
(`name`,`zh_name`,`en_name`,`desc`,`email`,`password`,`age`,`mobile`,`status`,`telephone`,`short_phone`,`employee_id`,`creater`,`modifier`,`modify_time`,`create_time`)
VALUES
('name002','name002','name002','name002','name002@test.com','wPfQv64jQZc2pYE15MTuTQ==',11,'15200483846',1,null,null,'F1334999','name01','name01',now(),now());

INSERT INTO `lamp`.`sys_user`
(`name`,`zh_name`,`en_name`,`desc`,`email`,`password`,`age`,`mobile`,`status`,`telephone`,`short_phone`,`employee_id`,`creater`,`modifier`,`modify_time`,`create_time`)
VALUES
('name003','name003','name003','name003','name003@test.com','wPfQv64jQZcSPq0YMWK+5A==',11,'15200483846',1,null,null,'F1334999','name01','name01',now(),now());



INSERT INTO `lamp`.`camera_type` (`id`, `code`, `name`, `order`, `desc`, `status`, `creater`, `modifier`, `modify_time`, `create_time`) VALUES ('1', 'IP', 'IP Camera', '1', 'IP Camera', '1', 'name001', 'name001', '2017-07-12', '2017-07-12');
INSERT INTO `lamp`.`camera_type` (`id`, `code`, `name`, `order`, `desc`, `status`, `creater`, `modifier`, `modify_time`, `create_time`) VALUES ('2', 'battery', 'Battery Camera', '2', 'Battery Camera', '1', 'name001', 'name001', '2017-07-12', '2017-07-12');

INSERT INTO `lamp`.`lamp_type` (`id`, `code`, `name`, `order`, `desc`, `status`, `creater`, `modifier`, `modify_time`, `create_time`) VALUES ('1', 'red', 'red', '1', 'red', '1', 'name001', 'name001', '2017-12-03', '2017-12-03');
INSERT INTO `lamp`.`lamp_type` (`id`, `code`, `name`, `order`, `desc`, `status`, `creater`, `modifier`, `modify_time`, `create_time`) VALUES ('2', 'blue', 'blue', '2', 'red', '1', 'name001', 'name001', '2017-12-03', '2017-12-03');
