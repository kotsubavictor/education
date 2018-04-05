DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `T1_STOCK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `T1_STOCK_CODE` varchar(10) NOT NULL,
  `T1_STOCK_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`T1_STOCK_ID`) USING BTREE,
  UNIQUE KEY `UNI_STOCK_NAME` (`T1_STOCK_NAME`),
  UNIQUE KEY `UNI_STOCK_ID` (`T1_STOCK_CODE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `backup_stock`;
CREATE TABLE `backup_stock` (
  `T1_STOCK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `T1_STOCK_CODE` varchar(10) NOT NULL,
  `T1_STOCK_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`T1_STOCK_ID`) USING BTREE,
  UNIQUE KEY `UNI_STOCK_NAME` (`T1_STOCK_NAME`),
  UNIQUE KEY `UNI_STOCK_ID` (`T1_STOCK_CODE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7052, 'PADINI1');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7053, 'PADINI2');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7054, 'PADINI3');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7055, 'PADINI4');
insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7056, 'PADINI5');

insert into hibernate.backup_stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7057, 'PADINI6');
insert into hibernate.backup_stock (T1_STOCK_CODE, T1_STOCK_NAME) values (7058, 'PADINI7');
