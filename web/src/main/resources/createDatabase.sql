/*
Arthur: zhuang_yx
Data: 2017/01/17
Description: Initial Database and Table
*/

# Database
CREATE DATABASE IF NOT EXISTS order_analysis CHARACTER SET utf8;
USE order_analysis;
# Table
# Customer Information
CREATE TABLE IF NOT EXISTS `Customer` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL,
	`sex` ENUM('male', 'female') NOT NULL,
	`age` TINYINT UNSIGNED NOT NULL,
	`address` VARCHAR(50) NOT NULL,
	 PRIMARY KEY (`id`)
);

# Add customer information
INSERT INTO `Customer` (`name`, `sex`, `age`, `address`) VALUE ('zhuang_yx', 'male', 22, '广东省深圳市宝安区建安二路富贵园D1-1003');

# Goods Type Information
CREATE TABLE IF NOT EXISTS `Goods_Type` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`desc` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY (`desc`)
);

# Add goods type
INSERT INTO `Goods_Type` (`desc`) VALUES ('手机'),('电器'),('家具');

# Supplier Information
CREATE TABLE IF NOT EXISTS `Supplier` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL,
	`phone` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY (`name`)
);
# Add suppliers
INSERT INTO `Supplier` (`name`, `phone`) VALUES 
('APPLE', '0755-00000000'),
('SUMSANG', '0755-00000001');

# Goods information
CREATE TABLE IF NOT EXISTS `Goods` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`type` BIGINT UNSIGNED NOT NULL,
	`supplier` BIGINT UNSIGNED NOT NULL,
	`name` VARCHAR(30) NOT NULL,
	`desc` TEXT NULL,
	`price` DOUBLE NOT NULL,
	`stock` INT UNSIGNED NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FOREIGN KEY (`type`) REFERENCES `Goods`(`id`),
	CONSTRAINT FOREIGN KEY (`supplier`) REFERENCES `Supplier`(`id`)
);

# Add goods
INSERT INTO `Goods` (`type`, `supplier`, `name`, `desc`, `price`, `stock`) VALUES 
(1, 1, '苹果7', '肾七', 9999, 20),
(1, 2, '三星Note7', '炸弹星', 7000, 90);

# Order information
CREATE TABLE IF NOT EXISTS `Order` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`customer` BIGINT UNSIGNED NOT NULL,
	`create_time` DATETIME NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FOREIGN KEY (`customer`) REFERENCES `Customer`(`id`)
);

# Add Order
INSERT INTO `Order` (`customer`, `create_time`) VALUE (1, NOW());

# Order Detail information
CREATE TABLE IF NOT EXISTS `Order_Detail` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`order_id` BIGINT UNSIGNED NOT NULL,
	`goods_id` BIGINT UNSIGNED NOT NULL,
	`goods_count` INT UNSIGNED NOT NULL,
	`goods_price` DOUBLE NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FOREIGN KEY (`order_id`) REFERENCES `Order`(`id`),
	CONSTRAINT FOREIGN KEY (`goods_id`) REFERENCES `Goods`(`id`)
);
# Add Order Detail
INSERT INTO `Order_Detail` (`order_id`, `goods_id`, `goods_count`, `goods_price`) VALUES 
(1, 1, 1, 9999.0),
(1, 2, 1, 7000.0);