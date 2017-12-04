/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50547
Source Host           : 127.0.0.1:3306
Source Database       : crawler_colony

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-12-04 21:49:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawler_job
-- ----------------------------
DROP TABLE IF EXISTS `crawler_job`;
CREATE TABLE `crawler_job` (
  `jobId` varchar(255) NOT NULL DEFAULT '' COMMENT 'job Id 唯一',
  `name` varchar(255) DEFAULT NULL COMMENT 'job 名称',
  `period` varchar(255) DEFAULT NULL COMMENT 'job 周期 毫秒单位',
  `nextStartDate` datetime DEFAULT NULL COMMENT 'job 下一个周次执行时间',
  `status` varchar(255) DEFAULT NULL COMMENT 'job  状态',
  `priority` int(11) DEFAULT NULL COMMENT 'job 优先级',
  `maxThread` int(11) DEFAULT NULL COMMENT 'job  最大使用线程数',
  `note` text COMMENT 'job 备注',
  `projectId` int(11) DEFAULT NULL COMMENT 'job 所属项目id ',
  `sourceId` int(11) DEFAULT NULL COMMENT 'job 数据源id',
  `createDate` datetime DEFAULT NULL COMMENT 'job创建时间',
  PRIMARY KEY (`jobId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawler_job_task
-- ----------------------------
DROP TABLE IF EXISTS `crawler_job_task`;
CREATE TABLE `crawler_job_task` (
  `jobId` varchar(255) NOT NULL DEFAULT '' COMMENT 'jobId',
  `taskName` varchar(77) NOT NULL COMMENT 'taskName',
  `code` text COMMENT '脚本代码',
  `bytes` mediumblob COMMENT '脚本二进制',
  `date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`jobId`,`taskName`),
  KEY `taskName` (`taskName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawler_log
-- ----------------------------
DROP TABLE IF EXISTS `crawler_log`;
CREATE TABLE `crawler_log` (
  `jobId` varchar(180) NOT NULL COMMENT 'jobId',
  `flag` bigint(20) NOT NULL COMMENT 'job周期 启动的时间戳',
  `taskName` varchar(150) NOT NULL COMMENT 'taskName',
  `successCount` int(255) DEFAULT NULL COMMENT 'task 执行成功次数',
  `dbCount` int(255) DEFAULT NULL,
  `failCount` int(255) DEFAULT NULL COMMENT 'task执行失败次数',
  `toDoCount` int(255) DEFAULT NULL COMMENT 'task 剩余个数',
  `createDate` datetime DEFAULT NULL COMMENT '日志创建时间',
  `modifiedDate` datetime DEFAULT NULL COMMENT '日志更新时间',
  PRIMARY KEY (`jobId`,`flag`,`taskName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawler_phone_grade_list
-- ----------------------------
DROP TABLE IF EXISTS `crawler_phone_grade_list`;
CREATE TABLE `crawler_phone_grade_list` (
  `phone` varchar(15) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `_createDate` datetime DEFAULT NULL,
  KEY `phone` (`phone`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawler_phone_list2
-- ----------------------------
DROP TABLE IF EXISTS `crawler_phone_list2`;
CREATE TABLE `crawler_phone_list2` (
  `phone` varchar(12) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `_createDate` datetime DEFAULT NULL,
  UNIQUE KEY `phone_index` (`phone`),
  KEY `grade_index` (`grade`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawler_phone_list3
-- ----------------------------
DROP TABLE IF EXISTS `crawler_phone_list3`;
CREATE TABLE `crawler_phone_list3` (
  `phone` varchar(255) DEFAULT NULL,
  `grade` int(255) DEFAULT NULL,
  `grade2` int(255) DEFAULT NULL,
  `grade3` int(255) DEFAULT NULL,
  `_createDate` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `projectId` int(11) NOT NULL COMMENT '项目id',
  `kid` int(11) NOT NULL AUTO_INCREMENT COMMENT '关键词id',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键词',
  `note` varchar(255) DEFAULT NULL COMMENT '关键词注释',
  `createDate` datetime DEFAULT NULL COMMENT '关键词创建时间',
  PRIMARY KEY (`projectId`,`kid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `projectId` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`projectId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source
-- ----------------------------
DROP TABLE IF EXISTS `source`;
CREATE TABLE `source` (
  `sourceId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`sourceId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test_table
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table` (
  `a` text,
  `b` text,
  `c` text,
  `d` text,
  `_createDate` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
