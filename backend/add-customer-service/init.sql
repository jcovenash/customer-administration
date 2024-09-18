CREATE SCHEMA `customers-db`;

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    paternal_surname VARCHAR(100),
    maternal_surname VARCHAR(100),
    date_birth DATE,
    sex VARCHAR(10)
);