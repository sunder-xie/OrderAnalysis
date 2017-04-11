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
-- INSERT INTO `Customer` (`name`, `sex`, `age`, `address`) 
-- VALUES 
-- ('ZhuangYiXin', 'male', 22, '广东省深圳市宝安区建安二路富贵园D1-1003'),
-- ('Sona','female',27,'北京市东花市北里20号楼6单元501室'),
-- ('Monkey D Luffy','male',20,'广东省广州市天河区天河路太古汇一座L18');

# Goods Type Information
CREATE TABLE IF NOT EXISTS `Goods_Type` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`desc` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY (`desc`)
);
desc
# Add goods type
-- INSERT INTO `Goods_Type` (`desc`) VALUES 
-- ('手机'),
-- ('电器'),
-- ('家具'),
-- ('生活用品'),
-- ('食物');

# Supplier Information
CREATE TABLE IF NOT EXISTS `Supplier` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL,
	`phone` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY (`name`)
);
# Add suppliers
-- INSERT INTO `Supplier` (`name`, `phone`) VALUES 
-- ('苹果', '0755-00000000'),
-- ('三星', '0755-00000001'),
-- ('宜家', '0755-00000002'),
-- ('沃尔玛', '0755-00000003'),
-- ('天虹', '0755-00000004'),
-- ('海尔', '0755-00000005'),
-- ('西门子', '0755-00000006');

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
	CONSTRAINT FOREIGN KEY (`type`) REFERENCES `Goods_Type`(`id`),
	CONSTRAINT FOREIGN KEY (`supplier`) REFERENCES `Supplier`(`id`)
);

# Add goods
-- INSERT INTO `Goods` (`type`, `supplier`, `name`, `desc`, `price`, `stock`) VALUES 
-- (1, 1, '苹果7', '超级贵', 7680.8, 3322),
-- (1, 2, '三星Note7', '会爆炸', 6722, 4244),
-- (2, 6, '海尔冰箱', '变态冰', 5781, 88),
-- (3, 3, '宜家凳子', '真皮的', 230, 79),
-- (5, 4, '薯片', '很上火', 12, 1200),
-- (5, 5, '卷心菜', '很上火', 5, 400),
-- (4, 4, '洗衣粉', '不伤手', 11.3, 789),
-- (2, 7, '剃须刀', '锋利的', 133, 12),
-- (4, 5, '扫把', '爱干净', 20, 37);

# Order information
CREATE TABLE IF NOT EXISTS `Orders` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`customer` BIGINT UNSIGNED NOT NULL,
	`create_time` DATETIME NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FOREIGN KEY (`customer`) REFERENCES `Customer`(`id`)
);

# Add Order
-- INSERT INTO `Orders` (`customer`, `create_time`) VALUES 
-- (1, DATE_ADD(NOW(),INTERVAL -4 DAY)),
-- (2, DATE_ADD(NOW(),INTERVAL -2 DAY)),
-- (1, DATE_ADD(NOW(),INTERVAL -1 DAY)),
-- (3, NOW());

# Order Detail information
CREATE TABLE IF NOT EXISTS `Order_Detail` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`order_id` BIGINT UNSIGNED NOT NULL,
	`goods_id` BIGINT UNSIGNED NOT NULL,
	`goods_count` INT UNSIGNED NOT NULL,
	`goods_price` DOUBLE NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FOREIGN KEY (`order_id`) REFERENCES `Orders`(`id`),
	CONSTRAINT FOREIGN KEY (`goods_id`) REFERENCES `Goods`(`id`)
);
# Add Order Detail
-- INSERT INTO `Order_Detail` (`order_id`, `goods_id`, `goods_count`, `goods_price`) VALUES 
-- (1, 1, 1, 1234.0),
-- (1, 3, 1, 45656.0),
-- (1, 5, 1, 456456.0),
-- (2, 2, 1, 546.0),
-- (2, 4, 1, 45645.0),
-- (3, 1, 1, 546.0),
-- (3, 2, 1, 4564.0),
-- (3, 7, 1, 456.0),
-- (3, 9, 1, 45645.0),
-- (4, 8, 1, 45645.0),
-- (4, 2, 1, 4567.0),
-- (4, 6, 1, 4568.0),
-- (4, 9, 1, 456456.0),
-- (4, 5, 1, 45678.0);

# Create configure database
CREATE TABLE IF NOT EXISTS `Configure` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	`content` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`)
);
INSERT INTO configure (`name`,`content`)values
('switch.customer.analysis','true'),
('switch.goos.type.analysis','true'),
('switch.order.analysis','true');