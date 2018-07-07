CREATE DATABASE IF NOT EXISTS `springbootdb_cluster` DEFAULT CHARACTER SET utf8;

USE springbootdb_cluster;

GRANT ALL ON springbootdb_cluster.* TO 'springbootdb_cluster'@'localhost' identified by 'springbootdb_cluster'; 

commit;
