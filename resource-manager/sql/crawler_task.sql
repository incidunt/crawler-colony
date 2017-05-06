/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : crawler-colony

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-05-04 12:22:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawler_task
-- ----------------------------
DROP TABLE IF EXISTS `crawler_task`;
CREATE TABLE `crawler_task` (
  `taskId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `period` bigint(20) NOT NULL,
  `projectId` int(11) DEFAULT NULL,
  `sourceId` int(11) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `nextStartDate` timestamp NULL DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`taskId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crawler_task
-- ----------------------------
INSERT INTO `crawler_task` VALUES ('sina_weibo', '新浪微博', '777777777', '0', '0', '2017-05-03 19:18:24', null, '0');
INSERT INTO `crawler_task` VALUES ('task_test_1', 'tasl test 1', '123', '0', '0', '2017-05-03 17:36:15', null, '0');
INSERT INTO `crawler_task` VALUES ('sina_weibo122', 'dang name 222', '123123', '0', '0', '2017-05-03 16:07:35', '2017-05-03 16:07:35', '3');
