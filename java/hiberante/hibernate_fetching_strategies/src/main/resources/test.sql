DROP TABLE IF EXISTS `hibernate`.`stock_daily_record`;
DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `T1_STOCK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `T1_STOCK_CODE` varchar(10) NOT NULL,
  `T1_STOCK_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`T1_STOCK_ID`) USING BTREE,
  UNIQUE KEY `UNI_STOCK_NAME` (`T1_STOCK_NAME`),
  UNIQUE KEY `UNI_STOCK_ID` (`T1_STOCK_CODE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

CREATE TABLE  `hibernate`.`stock_daily_record` (
  `T2_RECORD_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `T2_PRICE_OPEN` float(6,2) DEFAULT NULL,
  `T2_PRICE_CLOSE` float(6,2) DEFAULT NULL,
  `T2_PRICE_CHANGE` float(6,2) DEFAULT NULL,
  `T2_VOLUME` bigint(20) unsigned DEFAULT NULL,
  `T2_DATE` date NOT NULL,
  `T2_STOCK_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`T2_RECORD_ID`) USING BTREE,
  UNIQUE KEY `UNI_STOCK_DAILY_DATE` (`T2_DATE`),
  KEY `FK_STOCK_TRANSACTION_STOCK_ID` (`T2_STOCK_ID`),
  CONSTRAINT `FK_STOCK_TRANSACTION_STOCK_ID` FOREIGN KEY (`T2_STOCK_ID`)
  REFERENCES `stock` (`T1_STOCK_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7052, 'PADINI1');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7053, 'PADINI2');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7054, 'PADINI3');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7055, 'PADINI4');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7056, 'PADINI5');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7057, 'PADINI6');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7058, 'PADINI7');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7059, 'PADINI8');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7060, 'PADINI9');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7061, 'PADINI10');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7062, 'PADINI11');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7063, 'PADINI12');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7064, 'PADINI13');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7065, 'PADINI14');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7066, 'PADINI15');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7067, 'PADINI16');

INSERT INTO hibernate.stock_daily_record (T2_PRICE_OPEN, T2_PRICE_CLOSE, T2_PRICE_CHANGE, T2_VOLUME, T2_DATE, T2_STOCK_ID) VALUES (10.4, 10.3, 10.2, 100, '2018-04-16', 34);
INSERT INTO hibernate.stock_daily_record (T2_PRICE_OPEN, T2_PRICE_CLOSE, T2_PRICE_CHANGE, T2_VOLUME, T2_DATE, T2_STOCK_ID) VALUES (10.4, 10.3, 10.2, 100, '2018-04-17', 34);
INSERT INTO hibernate.stock_daily_record (T2_PRICE_OPEN, T2_PRICE_CLOSE, T2_PRICE_CHANGE, T2_VOLUME, T2_DATE, T2_STOCK_ID) VALUES (10.4, 10.3, 10.2, 100, '2018-04-18', 34);
INSERT INTO hibernate.stock_daily_record (T2_PRICE_OPEN, T2_PRICE_CLOSE, T2_PRICE_CHANGE, T2_VOLUME, T2_DATE, T2_STOCK_ID) VALUES (10.4, 10.3, 10.2, 100, '2018-04-19', 34);
INSERT INTO hibernate.stock_daily_record (T2_PRICE_OPEN, T2_PRICE_CLOSE, T2_PRICE_CHANGE, T2_VOLUME, T2_DATE, T2_STOCK_ID) VALUES (10.4, 10.3, 10.2, 100, '2018-04-20', 34);