CREATE TABLE characters (
     character_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL,
     name VARCHAR(255) NOT NULL,
     gender VARCHAR(50),
     character_class VARCHAR(50) NOT NULL,
     level INT DEFAULT 1,
     experience INT DEFAULT 0,
     health INT NOT NULL,
     attack INT NOT NULL,
     defense INT NOT NULL,
     status VARCHAR(50) NOT NULL
);