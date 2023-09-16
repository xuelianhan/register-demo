DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS auth_account_role;
DROP TABLE IF EXISTS uri_resource;
DROP TABLE IF EXISTS auth_role_uri;

CREATE TABLE IF NOT EXISTS account(
    id        INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50),
    password  VARCHAR(100),
    name      VARCHAR(50),
    avatar    VARCHAR(100),
    telephone VARCHAR(20),
    email     VARCHAR(100),
    location  VARCHAR(100),
    deleted int(8) not null default 0,
    INDEX (username)
) engine = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `auth_role`(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `role_name`   VARCHAR(50) NOT NULL COMMENT 'name of role',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `description` VARCHAR(50) DEFAULT '' COMMENT 'description of role',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `auth_account_role`(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `account_id`  int(10) NOT NULL DEFAULT '0',
    `role_id`     int(10) NOT NULL DEFAULT '0',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `uri_resource`(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `parent_id`   int(10) NOT NULL DEFAULT '0' COMMENT 'the parent id of this record',
    `uri_name`    VARCHAR(50) NOT NULL COMMENT 'name of uri',
    `uri`         VARCHAR(1024) NOT NULL COMMENT 'uri',
    `source_from` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:this system',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `description` VARCHAR(50) DEFAULT '' COMMENT 'description of role',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `auth_role_uri`(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `role_id`     int(10) NOT NULL DEFAULT '0',
    `uri_id`      int(10) NOT NULL DEFAULT '0',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE UNIQUE INDEX account_user ON account (username);
CREATE UNIQUE INDEX account_email ON account (email);

##alter table account add column deleted int(8) not null default 0 after `location`;