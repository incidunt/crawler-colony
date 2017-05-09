/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : crawler_colony

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-05-09 16:19:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawler_job
-- ----------------------------
DROP TABLE IF EXISTS `crawler_job`;
CREATE TABLE `crawler_job` (
  `jobId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `nextStartDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `maxThread` int(11) DEFAULT NULL,
  `note` text,
  `projectId` int(11) DEFAULT NULL,
  `sourceId` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`jobId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawler_log
-- ----------------------------
DROP TABLE IF EXISTS `crawler_log`;
CREATE TABLE `crawler_log` (
  `jobId` int(11) NOT NULL,
  `flag` bigint(255) NOT NULL,
  `taskName` varchar(255) NOT NULL,
  `crawlerCount` int(255) DEFAULT NULL,
  `dbCount` int(255) DEFAULT NULL,
  `failCount` int(255) DEFAULT NULL,
  `toDoCount` int(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`jobId`,`flag`,`taskName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for job_task
-- ----------------------------
DROP TABLE IF EXISTS `job_task`;
CREATE TABLE `job_task` (
  `jobId` varchar(255) NOT NULL,
  `taskName` varchar(77) NOT NULL,
  `code` text,
  `bytes` blob,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`jobId`,`taskName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `projectId` int(11) NOT NULL,
  `kid` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
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
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
