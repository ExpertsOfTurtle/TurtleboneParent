-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: turtle
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `idactivity` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `result` longtext,
  `datetime` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `result1` bigint(20) DEFAULT NULL,
  `result2` bigint(20) DEFAULT NULL,
  `result3` bigint(20) DEFAULT NULL,
  `strresult1` varchar(256) DEFAULT NULL,
  `strresult2` varchar(256) DEFAULT NULL,
  `strresult3` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idactivity`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contract_no` varchar(20) NOT NULL COMMENT '协议号',
  `contract_name` varchar(50) NOT NULL COMMENT '协议名称',
  `contract_content` text NOT NULL COMMENT '协议内容',
  `contract_type` varchar(20) NOT NULL COMMENT '协议类型',
  `effective_date` datetime NOT NULL COMMENT '有效起期',
  `expired_date` datetime NOT NULL COMMENT '有效止期',
  `contract_status` smallint(6) NOT NULL COMMENT '协议状态',
  `contract_party` varchar(200) DEFAULT NULL COMMENT '签约方',
  `signed_time` varchar(200) DEFAULT NULL COMMENT '签约时间',
  `create_by` varchar(45) NOT NULL COMMENT '创建人',
  `update_by` varchar(45) DEFAULT NULL COMMENT '更新人',
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contract_activity`
--

DROP TABLE IF EXISTS `contract_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contactId` int(11) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `action` int(11) DEFAULT NULL COMMENT '0-拒绝，1-接受',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(45) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `start_time` bigint(20) DEFAULT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  `datetime` varchar(45) DEFAULT NULL,
  `problemId` int(11) DEFAULT NULL,
  `game_mode` varchar(45) DEFAULT NULL,
  `punishment_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `optiongroup`
--

DROP TABLE IF EXISTS `optiongroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optiongroup` (
  `groupid` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(45) NOT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`groupid`),
  UNIQUE KEY `groupid_UNIQUE` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `options` (
  `optionid` int(11) NOT NULL AUTO_INCREMENT,
  `groupid` int(11) NOT NULL,
  `optionname` varchar(1024) COLLATE utf8_bin NOT NULL,
  `probability` int(11) NOT NULL,
  PRIMARY KEY (`optionid`),
  UNIQUE KEY `idchoice_UNIQUE` (`optionid`),
  KEY `GROUP_ID_idx` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problems`
--

DROP TABLE IF EXISTS `problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problems` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `problem_no` varchar(256) DEFAULT NULL,
  `date` varchar(256) NOT NULL,
  `respondent` varchar(256) NOT NULL,
  `status` varchar(256) NOT NULL COMMENT '0:未看题，1:已阅题,2:已完成',
  `type` varchar(45) DEFAULT 'A',
  `deadline` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `punishment`
--

DROP TABLE IF EXISTS `punishment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `punishment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `subtype` varchar(100) DEFAULT NULL,
  `val` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sudoku`
--

DROP TABLE IF EXISTS `sudoku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sudoku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem` varchar(300) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `lastupdatetime` bigint(20) DEFAULT NULL,
  `bestresult` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `problem_UNIQUE` (`problem`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sudoku_result`
--

DROP TABLE IF EXISTS `sudoku_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sudoku_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `usetime` int(11) DEFAULT NULL,
  `gameid` int(11) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `details` longtext,
  `timestamp` bigint(20) DEFAULT NULL,
  `datetime` varchar(45) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `game_mode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(45) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `content` longtext,
  `createTime` varchar(45) DEFAULT NULL,
  `deadline` varchar(45) DEFAULT NULL,
  `difficulty` int(11) DEFAULT '1',
  `percentage` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0' COMMENT 'NEW,CLOSED,PUNISHED',
  `punishment_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_user`
--

DROP TABLE IF EXISTS `task_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskid` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `assignDatetime` varchar(45) DEFAULT NULL,
  `deadline` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `percentage` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) COLLATE utf8_bin NOT NULL,
  `login_pwd` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `nickname` longtext COLLATE utf8_bin,
  `createtime` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `usertype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`login_name`),
  UNIQUE KEY `login_name_UNIQUE` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-10 23:23:45
