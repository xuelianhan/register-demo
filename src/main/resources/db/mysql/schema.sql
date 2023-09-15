DROP TABLE IF EXISTS account;
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

CREATE UNIQUE INDEX account_user ON account (username);
CREATE UNIQUE INDEX account_email ON account (email);

##alter table account add column deleted int(8) not null default 0 after `location`;