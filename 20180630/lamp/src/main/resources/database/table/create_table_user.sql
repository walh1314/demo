
USE springbootdb;

CREATE TABLE user (
    id INT NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;



CREATE TABLE system_info (
    id INT NOT NULL,
    pid VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;