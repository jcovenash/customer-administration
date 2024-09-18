CREATE SCHEMA `contact-customers-db`;

CREATE TABLE contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(100)
);