CREATE DATABASE IF NOT EXISTS `rangiffler_photo`;
USE `rangiffler_photo`;

CREATE TABLE IF NOT EXISTS photos
(
    id           BINARY(16) PRIMARY KEY,
    username     VARCHAR(50) UNIQUE NOT NULL,
    country_code VARCHAR(50)        NOT NULL,
    description  VARCHAR(255)       NOT NULL,
    photo        LONGBLOB
);


