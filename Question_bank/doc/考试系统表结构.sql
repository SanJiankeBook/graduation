/*
Navicat MySQL Data Transfer

Source Server         : a
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : examtest

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2016-07-10 11:02:49
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `adailytalk`
-- ----------------------------
DROP TABLE IF EXISTS `adailytalk`;
CREATE TABLE `adailytalk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assessment` varchar(1000) COLLATE utf8_bin DEFAULT '',
  `dateinfo` tinyblob,
  `descript` longtext COLLATE utf8_bin,
  `knowledge` longtext COLLATE utf8_bin NOT NULL,
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `speakdate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_760pbdxeprywi1cpsabhr6kuo` (`cid`),
  CONSTRAINT `FK_760pbdxeprywi1cpsabhr6kuo` FOREIGN KEY (`cid`) REFERENCES `examineeclass` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of adailytalk
-- ----------------------------

-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `authorityName` varchar(20) NOT NULL,
  `bound` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_Authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------

-- ----------------------------
-- Table structure for `chapter`
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `chapterName` varchar(50) NOT NULL,
  `subjectId` int(10) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_Chapter` (`id`),
  KEY `FK_Chapter_subject` (`subjectId`),
  CONSTRAINT `chapter_ibfk_1` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter
-- ----------------------------

-- ----------------------------
-- Table structure for `checking`
-- ----------------------------
DROP TABLE IF EXISTS `checking`;
CREATE TABLE `checking` (
  `checkId` int(10) NOT NULL AUTO_INCREMENT,
  `semester` varchar(10) NOT NULL,
  `uid` int(10) NOT NULL,
  `cid` int(10) NOT NULL,
  `checkDate` date NOT NULL,
  `checkTime` varchar(10) NOT NULL,
  `checkResult` text NOT NULL,
  `checkRemark` text,
  `checkStatus` int(10) DEFAULT NULL,
  `checkFlag` text,
  `checkDescript` text,
  PRIMARY KEY (`checkId`),
  UNIQUE KEY `PK__Checking__C5E2502659FA5E80` (`checkId`),
  KEY `FK_Classes` (`cid`),
  KEY `FK_SystemId` (`uid`),
  CONSTRAINT `checking_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `examineeclass` (`Id`),
  CONSTRAINT `checking_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `systemuser` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of checking
-- ----------------------------

-- ----------------------------
-- Table structure for `check_constraints`
-- ----------------------------
DROP TABLE IF EXISTS `check_constraints`;
CREATE TABLE `check_constraints` (
  `CONSTRAINT_CATALOG` varchar(128) DEFAULT NULL,
  `CONSTRAINT_SCHEMA` varchar(128) DEFAULT NULL,
  `CONSTRAINT_NAME` varchar(128) NOT NULL,
  `CHECK_CLAUSE` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_constraints
-- ----------------------------

-- ----------------------------
-- Table structure for `edition`
-- ----------------------------
DROP TABLE IF EXISTS `edition`;
CREATE TABLE `edition` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `editionName` varchar(50) NOT NULL,
  `currentUse` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_edition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of edition
-- ----------------------------

-- ----------------------------
-- Table structure for `examinee`
-- ----------------------------
DROP TABLE IF EXISTS `examinee`;
CREATE TABLE `examinee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `classId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_examinee` (`id`),
  UNIQUE KEY `UK_dx3742eyrthpresfnb9695fca` (`name`,`classId`),
  KEY `FK_Examinee_ExamineeClass` (`classId`),
  CONSTRAINT `examinee_ibfk_1` FOREIGN KEY (`classId`) REFERENCES `examineeclass` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examinee
-- ----------------------------

-- ----------------------------
-- Table structure for `examineeclass`
-- ----------------------------
DROP TABLE IF EXISTS `examineeclass`;
CREATE TABLE `examineeclass` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `className` varchar(20) NOT NULL,
  `semester` varchar(50) NOT NULL,
  `createDate` varchar(20) DEFAULT NULL,
  `overDate` varchar(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `PK_classInfo` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examineeclass
-- ----------------------------

-- ----------------------------
-- Table structure for `labanswer`
-- ----------------------------
DROP TABLE IF EXISTS `labanswer`;
CREATE TABLE `labanswer` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `paperId` varchar(50) NOT NULL,
  `examineeName` varchar(20) NOT NULL,
  `projectName` varchar(50) DEFAULT NULL,
  `code` text,
  `score` float DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_LabAnswer` (`id`),
  KEY `FK_LabAnswer_LabPaper` (`paperId`),
  CONSTRAINT `labanswer_ibfk_1` FOREIGN KEY (`paperId`) REFERENCES `labpaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of labanswer
-- ----------------------------

-- ----------------------------
-- Table structure for `labpaper`
-- ----------------------------
DROP TABLE IF EXISTS `labpaper`;
CREATE TABLE `labpaper` (
  `id` varchar(50) NOT NULL,
  `examineeClass` varchar(50) NOT NULL,
  `paperName` varchar(50) NOT NULL,
  `paperPwd` varchar(50) NOT NULL,
  `paperStatus` int(10) NOT NULL,
  `questionId` int(10) NOT NULL,
  `skillCodeId` int(10) DEFAULT NULL,
  `examDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `timesConsume` int(10) NOT NULL,
  `scorePercent` varchar(255) DEFAULT NULL,
  `avgScore` float DEFAULT NULL,
  `maxScore` float DEFAULT NULL,
  `minScore` float DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `operator` varchar(50) DEFAULT NULL,
  `questioned` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_LabPaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of labpaper
-- ----------------------------

-- ----------------------------
-- Table structure for `labquestion`
-- ----------------------------
DROP TABLE IF EXISTS `labquestion`;
CREATE TABLE `labquestion` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `editionId` int(10) NOT NULL,
  `semester` varchar(50) NOT NULL,
  `questionTitle` varchar(255) NOT NULL,
  `question` text NOT NULL,
  `image` text,
  `skillCodeId` int(10) NOT NULL,
  `difficulty` int(10) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_LabQuestion` (`id`),
  KEY `FK_LabQuestion_Edition` (`editionId`),
  KEY `FK_LabQuestion_LabQuestionType` (`skillCodeId`),
  CONSTRAINT `labquestion_ibfk_1` FOREIGN KEY (`editionId`) REFERENCES `edition` (`id`),
  CONSTRAINT `labquestion_ibfk_2` FOREIGN KEY (`skillCodeId`) REFERENCES `labquestiontype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of labquestion
-- ----------------------------

-- ----------------------------
-- Table structure for `labquestiontype`
-- ----------------------------
DROP TABLE IF EXISTS `labquestiontype`;
CREATE TABLE `labquestiontype` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `skillCode` varchar(50) NOT NULL,
  `semester` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_LabQuestionType` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of labquestiontype
-- ----------------------------

-- ----------------------------
-- Table structure for `pointanswer`
-- ----------------------------
DROP TABLE IF EXISTS `pointanswer`;
CREATE TABLE `pointanswer` (
  `opid` int(10) NOT NULL AUTO_INCREMENT,
  `pid` int(10) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `classId` int(10) DEFAULT NULL,
  `answer` text,
  `idea` text,
  `remark` varchar(200) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `aname` longtext,
  `claddid` int(11) DEFAULT NULL,
  PRIMARY KEY (`opid`),
  UNIQUE KEY `PK__PointAns__4D08CC1E4222D4EF` (`opid`),
  KEY `FK__PointAnswer__44FF419A` (`name`,`classId`),
  KEY `FK_pid` (`pid`),
  CONSTRAINT `pointanswer_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `pointpaper` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pointanswer
-- ----------------------------

-- ----------------------------
-- Table structure for `pointinfo`
-- ----------------------------
DROP TABLE IF EXISTS `pointinfo`;
CREATE TABLE `pointinfo` (
  `pid` int(10) NOT NULL AUTO_INCREMENT,
  `cid` int(10) DEFAULT NULL,
  `pcontent` varchar(200) NOT NULL,
  `status` int(10) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `PK__PointInf__DD37D91A49C3F6B7` (`pid`),
  KEY `fk_subId` (`cid`),
  CONSTRAINT `pointinfo_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pointinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `pointpaper`
-- ----------------------------
DROP TABLE IF EXISTS `pointpaper`;
CREATE TABLE `pointpaper` (
  `pid` int(10) NOT NULL AUTO_INCREMENT,
  `sid` int(10) DEFAULT NULL,
  `cid` int(10) DEFAULT NULL,
  `pname` varchar(100) NOT NULL,
  `ptitle` text NOT NULL,
  `pdate` date DEFAULT NULL,
  `paperPwd` varchar(50) NOT NULL,
  `pstatus` int(10) DEFAULT NULL,
  `descript` text,
  `remark` varchar(200) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `PK__PointPap__DD37D91A3C69FB99` (`pid`),
  KEY `FK_cID` (`sid`),
  KEY `FK_sID` (`cid`),
  KEY `FK_sahstwr54vn6r7lotthmm06l5` (`sid`),
  CONSTRAINT `FK_sahstwr54vn6r7lotthmm06l5` FOREIGN KEY (`sid`) REFERENCES `subject` (`id`),
  CONSTRAINT `pointpaper_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `chapter` (`id`),
  CONSTRAINT `pointpaper_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `examineeclass` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pointpaper
-- ----------------------------

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `subjectName` varchar(100) NOT NULL,
  `chapterCount` int(10) NOT NULL,
  `semester` varchar(50) NOT NULL,
  `editionId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_subject` (`id`),
  KEY `FK_subject_Edition` (`editionId`),
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`editionId`) REFERENCES `edition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------

-- ----------------------------
-- Table structure for `sysdiagrams`
-- ----------------------------
DROP TABLE IF EXISTS `sysdiagrams`;
CREATE TABLE `sysdiagrams` (
  `name` varchar(128) NOT NULL,
  `principal_id` int(10) NOT NULL,
  `diagram_id` int(10) NOT NULL AUTO_INCREMENT,
  `version` int(10) DEFAULT NULL,
  `definition` blob,
  PRIMARY KEY (`diagram_id`),
  UNIQUE KEY `PK__sysdiagr__C2B05B61656C112C` (`diagram_id`),
  UNIQUE KEY `UK_principal_name` (`principal_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysdiagrams
-- ----------------------------

-- ----------------------------
-- Table structure for `systemuser`
-- ----------------------------
DROP TABLE IF EXISTS `systemuser`;
CREATE TABLE `systemuser` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL DEFAULT 'accpteacher',
  `authorities` varchar(20) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_SystemUser` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemuser
-- ----------------------------
INSERT INTO systemuser VALUES ('1', 'admin', '25D55AD283AA400AF464C76D713C07AD', '1', '教员');
INSERT INTO systemuser VALUES ('2', '周海军', '2F34C1F0F40C06B0314EEFA9924E5532', '1', '教员');
INSERT INTO systemuser VALUES ('3', '刘源', '25D55AD283AA400AF464C76D713C07AD', '1', '教员');
INSERT INTO systemuser VALUES ('5', '薛迪', 'C9D2124A3532181D869284C4FE7A31A7', '1', '教员');
INSERT INTO systemuser VALUES ('6', '姜平', '3DBE00A167653A1AAEE01D93E77E730E', '1', '教员');
INSERT INTO systemuser VALUES ('8', 'sys', '759415CF5F0685E91F6F85432A7AEA59', '2', '管理员');
INSERT INTO systemuser VALUES ('9', 'b', 'b', '1', '教员');
INSERT INTO systemuser VALUES ('10', 'c', 'c', '2', '管理员');

-- ----------------------------
-- Table structure for `temp`
-- ----------------------------
DROP TABLE IF EXISTS `temp`;
CREATE TABLE `temp` (
  `ppid` int(10) DEFAULT NULL,
  `sname` varchar(20) DEFAULT NULL,
  `classid` int(10) DEFAULT NULL,
  `className` varchar(20) DEFAULT NULL,
  `subid` int(10) DEFAULT NULL,
  `subname` varchar(100) DEFAULT NULL,
  `pointid` int(10) DEFAULT NULL,
  `pcontent` varchar(200) DEFAULT NULL,
  `grade` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of temp
-- ----------------------------

-- ----------------------------
-- Table structure for `vw_writingquestioninfo`
-- ----------------------------
DROP TABLE IF EXISTS `vw_writingquestioninfo`;
CREATE TABLE `vw_writingquestioninfo` (
  `id` int(10) NOT NULL,
  `semester` varchar(50) NOT NULL,
  `editionName` varchar(50) NOT NULL,
  `questionType` varchar(2) NOT NULL,
  `subjectName` varchar(100) NOT NULL,
  `chapterName` varchar(50) NOT NULL,
  `difficulty` int(10) DEFAULT NULL,
  `question` text NOT NULL,
  `optionA` text NOT NULL,
  `optionB` text NOT NULL,
  `optionC` text NOT NULL,
  `optionD` text NOT NULL,
  `answer` varchar(20) DEFAULT NULL,
  `remark` text,
  `image` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vw_writingquestioninfo
-- ----------------------------

-- ----------------------------
-- Table structure for `writinganswer`
-- ----------------------------
DROP TABLE IF EXISTS `writinganswer`;
CREATE TABLE `writinganswer` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `paperId` varchar(50) NOT NULL,
  `examineeName` varchar(20) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `correctRate` varchar(255) DEFAULT NULL,
  `spareTime` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_WritingAnswer` (`id`),
  KEY `FK_WritingAnswer_WritingPaper` (`paperId`),
  CONSTRAINT `writinganswer_ibfk_1` FOREIGN KEY (`paperId`) REFERENCES `writingpaper` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=834 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of writinganswer
-- ----------------------------

-- ----------------------------
-- Table structure for `writingpaper`
-- ----------------------------
DROP TABLE IF EXISTS `writingpaper`;
CREATE TABLE `writingpaper` (
  `id` varchar(50) NOT NULL,
  `examineeClass` varchar(50) NOT NULL,
  `paperName` varchar(50) NOT NULL,
  `paperPwd` varchar(50) NOT NULL,
  `examSubject` varchar(200) NOT NULL DEFAULT '综合',
  `paperStatus` int(10) NOT NULL,
  `questionId` text NOT NULL,
  `questionInfo` text,
  `countOfQuestion` int(10) NOT NULL,
  `examDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `timesConsume` int(10) NOT NULL,
  `scorePercent` varchar(255) DEFAULT NULL,
  `questionCorrect` text,
  `avgScore` float DEFAULT NULL,
  `maxScore` float DEFAULT NULL,
  `minScore` float DEFAULT NULL,
  `correctRate` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `operator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_WriteExaminationPaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of writingpaper
-- ----------------------------

-- ----------------------------
-- Table structure for `writingquestion`
-- ----------------------------
DROP TABLE IF EXISTS `writingquestion`;
CREATE TABLE `writingquestion` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `editionId` int(10) NOT NULL,
  `semester` varchar(50) NOT NULL,
  `subjectId` int(10) NOT NULL,
  `chapterId` int(10) NOT NULL,
  `questionType` varchar(2) NOT NULL DEFAULT '',
  `question` text NOT NULL,
  `optionA` text NOT NULL,
  `optionB` text NOT NULL,
  `optionC` text NOT NULL,
  `optionD` text NOT NULL,
  `image` text,
  `answer` varchar(20) DEFAULT NULL,
  `difficulty` int(10) DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_WriteQuestions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1551 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of writingquestion
-- ----------------------------
