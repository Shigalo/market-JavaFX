CREATE SCHEMA `filonenko5` ;

use filonenko5;

CREATE TABLE user
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    login varchar(45) NOT NULL,
    password varchar(45) NOT NULL,
    access int NOT NULL
);

CREATE TABLE product
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(45) UNIQUE,
    firm varchar(45),
    unit_price double NULL
);

CREATE TABLE sale
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    product_id int NOT NULL,
    date date NOT NULL,
    seller_id int NOT NULL,
    quantity int NOT NULL,
    CONSTRAINT sales_product_id_fk FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT user_sale_fk FOREIGN KEY (seller_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE status
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(45)
);

CREATE TABLE guarantee
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    sale_id int NOT NULL,
    status_id int NOT NULL,
    date date NOT NULL,
    CONSTRAINT status_fk FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT sale_fk FOREIGN KEY (sale_id) REFERENCES sale (id)
);

INSERT INTO `filonenko5`.`status` (name) VALUES ('Действует');
INSERT INTO `filonenko5`.`status` (name) VALUES ('Закончилась');
INSERT INTO `filonenko5`.`status` (name) VALUES ('Замена');
INSERT INTO `filonenko5`.`status` (name) VALUES ('Возврат');
INSERT INTO `filonenko5`.`status` (name) VALUES ('Ремонт');

CREATE TABLE storage
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    product_id int NOT NULL UNIQUE,
    quantity int NOT NULL,
    CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE ON UPDATE CASCADE
);