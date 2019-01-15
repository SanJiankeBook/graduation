/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : novel

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2018-04-15 20:21:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adid` int(11) NOT NULL auto_increment,
  `adnumber` varchar(30) default NULL,
  `adpassword` varchar(30) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`adid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '1001', 'a', null, null, null);

-- ----------------------------
-- Table structure for `author`
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `aid` int(11) NOT NULL auto_increment,
  `uid` int(11) default NULL,
  `aname` varchar(30) default NULL,
  `pan_name` varchar(30) default NULL,
  `aage` int(11) default NULL,
  `agrade` varchar(20) default NULL,
  `acard` varchar(30) default NULL,
  `atel` varchar(30) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`aid`),
  KEY `uid` (`uid`),
  CONSTRAINT `author_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of author
-- ----------------------------

-- ----------------------------
-- Table structure for `novel`
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel` (
  `nid` int(11) NOT NULL auto_increment,
  `tid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `nname` varchar(30) default NULL,
  `npicture` varchar(100) default NULL,
  `ndescription` varchar(500) default NULL,
  `nstatus` varchar(15) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`nid`),
  KEY `tid` (`tid`),
  KEY `aid` (`aid`),
  CONSTRAINT `novel_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `novel_type` (`tid`),
  CONSTRAINT `novel_ibfk_2` FOREIGN KEY (`aid`) REFERENCES `author` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of novel
-- ----------------------------

-- ----------------------------
-- Table structure for `novel_chapter`
-- ----------------------------
DROP TABLE IF EXISTS `novel_chapter`;
CREATE TABLE `novel_chapter` (
  `cid` int(11) NOT NULL auto_increment,
  `nid` int(11) NOT NULL,
  `cname` varchar(100) default NULL,
  `caddress` varchar(300) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`cid`),
  KEY `nid` (`nid`),
  CONSTRAINT `novel_chapter_ibfk_1` FOREIGN KEY (`nid`) REFERENCES `novel` (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of novel_chapter
-- ----------------------------

-- ----------------------------
-- Table structure for `novel_type`
-- ----------------------------
DROP TABLE IF EXISTS `novel_type`;
CREATE TABLE `novel_type` (
  `tid` int(11) NOT NULL auto_increment,
  `tname` varchar(15) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of novel_type
-- ----------------------------
INSERT INTO `novel_type` VALUES ('1', 'java', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('2', 'c++', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('3', 'c', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('4', 'c#', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('5', 'web', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('6', 'PHP', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('7', ' Python ', '1', '1', '1');
INSERT INTO `novel_type` VALUES ('8', '其他', '1', '1', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL auto_increment,
  `uname` varchar(30) default NULL,
  `u_number` varchar(30) default NULL,
  `upassword` varchar(30) default NULL,
  `usex` varchar(10) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_book`
-- ----------------------------
DROP TABLE IF EXISTS `user_book`;
CREATE TABLE `user_book` (
  `ubid` int(11) NOT NULL auto_increment,
  `nid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `ubdate` varchar(30) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` int(11) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`ubid`),
  KEY `uid` (`uid`),
  KEY `nid` (`nid`),
  CONSTRAINT `user_book_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`),
  CONSTRAINT `user_book_ibfk_2` FOREIGN KEY (`nid`) REFERENCES `novel` (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_book
-- ----------------------------

-- ----------------------------
-- Table structure for `user_talk`
-- ----------------------------
DROP TABLE IF EXISTS `user_talk`;
CREATE TABLE `user_talk` (
  `utid` int(11) NOT NULL auto_increment,
  `nid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `utdate` varchar(30) default NULL,
  `utcontent` varchar(500) default NULL,
  `standby_1` varchar(100) default NULL,
  `standby_2` varchar(100) default NULL,
  `standby_3` varchar(100) default NULL,
  PRIMARY KEY  (`utid`),
  KEY `uid` (`uid`),
  KEY `nid` (`nid`),
  CONSTRAINT `user_talk_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`),
  CONSTRAINT `user_talk_ibfk_2` FOREIGN KEY (`nid`) REFERENCES `novel` (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_talk
-- ----------------------------
