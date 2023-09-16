DROP TABLE account IF EXISTS;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS auth_account_role;
DROP TABLE IF EXISTS uri_resource;
DROP TABLE IF EXISTS auth_role_uri;

CREATE TABLE account
(
    id        INTEGER IDENTITY PRIMARY KEY,
    username  VARCHAR(50),
    password  VARCHAR(100),
    name      VARCHAR(50),
    avatar    VARCHAR(100),
    telephone VARCHAR(20),
    email     VARCHAR(100),
    location  VARCHAR(100),
    deleted INTEGER
);

CREATE UNIQUE INDEX account_user ON account (username);
CREATE UNIQUE INDEX account_email ON account (email);

CREATE TABLE IF NOT EXISTS `auth_role`(
    `id`          INTEGER IDENTITY PRIMARY KEY,
    `role_name`   VARCHAR(50),
    `create_time` DATETIME,
    `description` VARCHAR(50),
     PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `auth_account_role`(
    `id`          INTEGER IDENTITY PRIMARY KEY,
    `account_id`  INTEGER,
    `role_id`     INTEGER,
    `create_time` DATETIME,
    `update_time` DATETIME,
     PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `uri_resource`(
    `id`          INTEGER IDENTITY PRIMARY KEY,
    `parent_id`   INTEGER,
    `uri_name`    VARCHAR(50),
    `uri`         VARCHAR(1024),
    `source_from` INTEGER,
    `create_time` DATETIME,
    `update_time` DATETIME,
    `description` VARCHAR(50),
     PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `auth_role_uri`(
    `id`          INTEGER IDENTITY PRIMARY KEY,
    `role_id`     INTEGER,
    `uri_id`      INTEGER,
    `create_time` DATETIME,
     PRIMARY KEY (`id`)
);
