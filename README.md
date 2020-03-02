# InitArea
使用高德地图的公用api在本地数据库录入省市区信息

```sql
CREATE TABLE `area_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(1) DEFAULT '3' COMMENT '0：省 1：市 2：区',
  `name` varchar(16) DEFAULT NULL,
  `code` varchar(16) DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

