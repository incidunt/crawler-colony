/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : crawler_colony

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-06-18 14:03:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawler_data
-- ----------------------------
DROP TABLE IF EXISTS `crawler_data`;
CREATE TABLE `crawler_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` varchar(64) DEFAULT NULL,
  `ckey` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cdata` text,
  PRIMARY KEY (`id`),
  KEY `idx_ckey` (`ckey`) USING BTREE,
  KEY `idx_jobId` (`jobId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_job
-- ----------------------------
DROP TABLE IF EXISTS `crawler_job`;
CREATE TABLE `crawler_job` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `period` int(11) DEFAULT NULL,
  `nextStartDate` datetime DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `maxThread` int(11) DEFAULT NULL,
  `note` varchar(1024) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  `sourceId` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx__jobId` (`jobId`) USING BTREE,
  KEY `idx__nextStartDate` (`nextStartDate`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_job_task
-- ----------------------------
DROP TABLE IF EXISTS `crawler_job_task`;
CREATE TABLE `crawler_job_task` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` varchar(64) NOT NULL,
  `taskName` varchar(64) NOT NULL,
  `code` text,
  `bytes` mediumblob,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jobId` (`jobId`) USING BTREE,
  KEY `idx_taskName` (`taskName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_keyword
-- ----------------------------
DROP TABLE IF EXISTS `crawler_keyword`;
CREATE TABLE `crawler_keyword` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projectId` int(11) DEFAULT NULL,
  `kid` int(11) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_projectId` (`projectId`) USING BTREE,
  KEY `idx_kid` (`kid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1234168 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_log
-- ----------------------------
DROP TABLE IF EXISTS `crawler_log`;
CREATE TABLE `crawler_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` varchar(64) NOT NULL,
  `flag` bigint(20) NOT NULL,
  `taskName` varchar(64) NOT NULL,
  `successCount` int(255) DEFAULT NULL,
  `dbCount` int(255) DEFAULT NULL,
  `failCount` int(255) DEFAULT NULL,
  `toDoCount` int(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jobId` (`jobId`) USING BTREE,
  KEY `idx_flag` (`flag`) USING BTREE,
  KEY `idx_taskName` (`taskName`) USING BTREE,
  KEY `idx_createDate` (`createDate`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_p2pblack_list
-- ----------------------------
DROP TABLE IF EXISTS `crawler_p2pblack_list`;
CREATE TABLE `crawler_p2pblack_list` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `phone` char(11) DEFAULT NULL,
  `registered` char(1) DEFAULT NULL,
  `source` varchar(64) DEFAULT NULL,
  `_createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_phone` (`phone`) USING BTREE,
  KEY `idx_registered` (`registered`) USING BTREE,
  KEY `idx_source` (`source`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19316 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_project
-- ----------------------------
DROP TABLE IF EXISTS `crawler_project`;
CREATE TABLE `crawler_project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_source
-- ----------------------------
DROP TABLE IF EXISTS `crawler_source`;
CREATE TABLE `crawler_source` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `url` varchar(128) NOT NULL,
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_url` (`url`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for crawler_user
-- ----------------------------
DROP TABLE IF EXISTS `crawler_user`;
CREATE TABLE `crawler_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=1;
