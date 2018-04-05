DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `T1_STOCK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `T1_STOCK_CODE` varchar(10) NOT NULL,
  `T1_STOCK_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`T1_STOCK_ID`) USING BTREE,
  UNIQUE KEY `UNI_STOCK_NAME` (`T1_STOCK_NAME`),
  UNIQUE KEY `UNI_STOCK_ID` (`T1_STOCK_CODE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hibernate`.`stock_daily_record`;
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
  KEY `FK_STOCK_TRANSACTION_STOCK_ID` (`T2_STOCK_ID`)
#   CONSTRAINT `FK_STOCK_TRANSACTION_STOCK_ID` FOREIGN KEY (`T2_STOCK_ID`)
#   REFERENCES `stock` (`T1_STOCK_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;