CREATE DATABASE IF NOT EXISTS `lamp` DEFAULT CHARACTER SET utf8;

USE lamp;

GRANT ALL ON lamp.* TO 'lamp'@'localhost' identified by 'lamp'; 

commit;
