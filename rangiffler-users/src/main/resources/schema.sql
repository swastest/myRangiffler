CREATE DATABASE IF NOT EXISTS `rangiffler_users`;
USE `rangiffler_users`;

CREATE TABLE IF NOT EXISTS users (
    id         BINARY(16) PRIMARY KEY,
    username   VARCHAR(50) UNIQUE NOT NULL,
    firstname  VARCHAR(255),
    lastname   VARCHAR(255),
    avatar     LONGBLOB
    );

CREATE TABLE IF NOT EXISTS friends (
    user_id     BINARY(16) NOT NULL,
    friend_id   BINARY(16) NOT NULL,
    pending     BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
    );
