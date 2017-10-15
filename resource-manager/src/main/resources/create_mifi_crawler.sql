/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : mifi_crawler

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-06-12 16:46:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cdata
-- ----------------------------
DROP TABLE IF EXISTS `cdata`;
CREATE TABLE `cdata` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` varchar(64) DEFAULT NULL,
  `ckey` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cdata` text,
  PRIMARY KEY (`id`),
  KEY `idx_ckey` (`ckey`) USING BTREE,
  KEY `idx_jobId` (`jobId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

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
-- Table structure for d_p2pblack_list
-- ----------------------------
DROP TABLE IF EXISTS `d_p2pblack_list`;
CREATE TABLE `d_p2pblack_list` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `phone` char(11) DEFAULT NULL,
  `registered` char(1) DEFAULT NULL,
  `source` varchar(64) DEFAULT NULL,
  `_createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_phone` (`phone`) USING BTREE,
  KEY `idx_registered` (`registered`) USING BTREE,
  KEY `idx_source` (`source`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for job_task
-- ----------------------------
DROP TABLE IF EXISTS `job_task`;
CREATE TABLE `job_task` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` varchar(64) NOT NULL,
  `taskName` varchar(64) NOT NULL,
  `code` text,
  `bytes` mediumblob,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jobId` (`jobId`) USING BTREE,
  KEY `idx_taskName` (`taskName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projectId` int(11) DEFAULT NULL,
  `kid` int(11) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_projectId` (`projectId`) USING BTREE,
  KEY `idx_kid` (`kid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for source
-- ----------------------------
DROP TABLE IF EXISTS `source`;
CREATE TABLE `source` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `url` varchar(128) NOT NULL,
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_url` (`url`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
SET FOREIGN_KEY_CHECKS=1;