CREATE TABLE product (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         description VARCHAR(255),
                         image_url VARCHAR(255),
                         name VARCHAR(255),
                         price DECIMAL(38,2),
                         rating DOUBLE,
                         rating_count INT
);
