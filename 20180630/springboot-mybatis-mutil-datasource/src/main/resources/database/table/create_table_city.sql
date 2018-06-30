
USE springbootdb_cluster;

CREATE TABLE city (
    id INT NOT NULL,
    province_id INT NOT NULL,
    city_name VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;