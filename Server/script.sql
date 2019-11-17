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

ALTER TABLE sale ADD `quantity` int NOT NULL;

CREATE TABLE status
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type varchar(45)
);

CREATE TABLE guarantee
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    sale_id int NOT NULL,
    status_id int NOT NULL,
    CONSTRAINT status_fk FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT sale_fk FOREIGN KEY (sale_id) REFERENCES sale (id)
);

INSERT INTO `filonenko5`.`status` (`type`) VALUES ('ACTS');
INSERT INTO `filonenko5`.`status` (`type`) VALUES ('ENDED');
INSERT INTO `filonenko5`.`status` (`type`) VALUES ('REPAIR');
INSERT INTO `filonenko5`.`status` (`type`) VALUES ('REPLACEMENT');