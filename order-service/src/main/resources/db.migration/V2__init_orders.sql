-- V2__init_orders.sql
CREATE TABLE orders (
                        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        created_at DATETIME(6),
                        total_price DECIMAL(38,2),
                        user_id BIGINT
);
