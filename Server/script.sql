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
    name UNIQUE INDEX varchar(45),
    firm varchar(45)
);

ALTER TABLE product ADD `unit_price` double NULL;

CREATE TABLE sale
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    product_id int NOT NULL,
    date date NOT NULL,
    CONSTRAINT sales_product_id_fk FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE ON UPDATE CASCADE
);