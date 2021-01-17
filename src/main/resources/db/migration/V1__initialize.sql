CREATE TABLE `category_tbl` (
  `category_id`         int unsigned        NOT NULL    AUTO_INCREMENT,
  `category`            varchar(255)        NOT NULL,

  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_title_UNIQUE` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;

INSERT INTO `market_db`.`category_tbl` (`category`) VALUES
('Аудиотехника'),
('Клавиатуры'),
('Мышки'),
('Разное')
;

CREATE TABLE `product_tbl` (
  `product_id`          bigint unsigned     NOT NULL    AUTO_INCREMENT,
  `title`               varchar(255)        NOT NULL,
  `price`               int unsigned        NOT NULL,
  `category_id`         int unsigned        NOT NULL,

  PRIMARY KEY (`product_id`),
  KEY `fk_category-id_product-category_idx` (`category_id`),
  CONSTRAINT `fk_category_product` FOREIGN KEY (`category_id`) REFERENCES `category_tbl` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;

INSERT INTO `market_db`.`product_tbl` (`title`, `price`, `category_id`) VALUES
('Наушники', '3000', '1'),
('Клавиатура', '2100', '2'),
('Мышь', '1600', '3'),
('Коврик', '1000', '4'),
('Микрофон', '1000', '1'),
('Гарнитура', '4000', '1'),
('Наушники2', '3100', '1'),
('Клавиатура2', '2100', '2'),
('Мышь2', '1800', '3'),
('Коврик2', '1100', '4'),
('Микрофон2', '1100', '1'),
('Гарнитура2', '4400', '1'),
('Наушники3', '3200', '1'),
('Клавиатура3', '2200', '2'),
('Мышь3', '1900', '3'),
('Коврик3', '1200', '4'),
('Микрофон3', '1200', '1'),
('Гарнитура3', '4500', '1'),
('Наушники4', '3500', '1'),
('Мышь6', '2600', '3'),
('Клавиатура5', '3100', '2'),
('Наушники5', '5000', '1')
;

CREATE TABLE `client_tbl` (
  `client_id`           bigint unsigned         NOT NULL    AUTO_INCREMENT,
  `client_name`         varchar(255)            NOT NULL,
  `password`            varchar(255)            NOT NULL,
  `phone_number`        varchar(255)            NOT NULL,
  `email`               varchar(255)            NOT NULL,

  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;
INSERT INTO `market_db`.`client_tbl` (`client_name`, `password`, `phone_number`, `email`) VALUES
('admin', '$2b$04$DFBuN3HF36KAcAzgkPTJdOC5NPPetr58gVEvRc5sbNMGXQodeEkYa', '1234567', 'admin@email.ru'),
('user1', '$2b$04$DFBuN3HF36KAcAzgkPTJdOC5NPPetr58gVEvRc5sbNMGXQodeEkYa', '0987654', 'user1@email.ru'),
('user2', '$2b$04$DFBuN3HF36KAcAzgkPTJdOC5NPPetr58gVEvRc5sbNMGXQodeEkYa', '2346790', 'user2@email.ru')
;

CREATE TABLE `role_tbl` (
  `role_id`             int unsigned            NOT NULL    AUTO_INCREMENT,
  `name`                varchar(255)            NOT NULL,

  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;

INSERT INTO `market_db`.`role_tbl` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER'),
('ROLE_BLOCKED_USER')
;

CREATE TABLE `client_role_tbl` (
  `client_role_id`      bigint unsigned         NOT NULL    AUTO_INCREMENT,
  `client_id`           bigint unsigned         NOT NULL,
  `role_id`             int unsigned            NOT NULL,

  PRIMARY KEY (`client_role_id`),
  UNIQUE KEY `client_role` (`client_id`,`role_id`),
  KEY `fk_role_cr_idx` (`role_id`),
  CONSTRAINT `fk_client_cr` FOREIGN KEY (`client_id`) REFERENCES `client_tbl` (`client_id`),
  CONSTRAINT `fk_role_cr` FOREIGN KEY (`role_id`) REFERENCES `role_tbl` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;

INSERT INTO `market_db`.`client_role_tbl` (`client_id`, `role_id`) VALUES
('1', '1'),
('2', '2'),
('3', '3')
;

CREATE TABLE `order_tbl` (
  `order_id`            bigint unsigned         NOT NULL    AUTO_INCREMENT,
  `client_id`           bigint unsigned         NOT NULL,
  `phone_number`        varchar(255)            NOT NULL,
  `address`             varchar(255)            NOT NULL,
  `price`               int unsigned            NOT NULL,

  PRIMARY KEY (`order_id`),
  KEY `fk_client_order_idx` (`client_id`),
  CONSTRAINT `fk_client_order` FOREIGN KEY (`client_id`) REFERENCES `client_tbl` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;

CREATE TABLE `order_item_tbl` (
  `order_item_id`       bigint unsigned         NOT NULL    AUTO_INCREMENT,
  `product_id`          bigint unsigned         NOT NULL,
  `quantity`            int unsigned            NOT NULL,
  `product_price`       int unsigned            NOT NULL,
  `total_cost`          int unsigned            NOT NULL,
  `order_id`            bigint unsigned         DEFAULT NULL,

  PRIMARY KEY (`order_item_id`),
  KEY `fk_product-id_orderitem-product_idx` (`product_id`),
  KEY `fk_order_order-item_idx` (`order_id`),
  CONSTRAINT `fk_order_order-item` FOREIGN KEY (`order_id`) REFERENCES `order_tbl` (`order_id`),
  CONSTRAINT `fk_product_order-item` FOREIGN KEY (`product_id`) REFERENCES `product_tbl` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;