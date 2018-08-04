CREATE DATABASE IF NOT EXISTS `mqtt` DEFAULT CHARACTER SET utf8;

USE mqtt;

GRANT ALL ON mqtt.* TO 'mqtt'@'localhost' identified by 'mqtt'; 

commit;
