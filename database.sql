-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: scu_cs_stu_info
-- ------------------------------------------------------
-- Server version	8.0.23

--
-- Table structure for table `basicinfo`
--

DROP TABLE IF EXISTS `basicinfo`;
CREATE TABLE `basicinfo` (
  `StudentId` char(13) NOT NULL,
  `Name` varchar(10) NOT NULL,
  `Gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Nationality` varchar(5) NOT NULL,
  `BirthDate` varchar(11) NOT NULL,
  `Class` varchar(5) NOT NULL,
  `Note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`StudentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `classwork`
--

DROP TABLE IF EXISTS `classwork`;
CREATE TABLE `classwork` (
  `StudentId` varchar(13) NOT NULL,
  `Name` varchar(10) NOT NULL,
  `Class` varchar(5) NOT NULL,
  `Work` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Dorm` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`StudentId`,`Work`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `detailinfo`
--

DROP TABLE IF EXISTS `detailinfo`;
CREATE TABLE `detailinfo` (
  `StudentId` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Dormitory` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Id` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BirthPlace` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PhoneNum` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PolStatus` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `HouseholdTransfer` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `SeniorSchool` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FatherName` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FatherPhone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FatherWorkPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FatherWorkPosition` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MotherName` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MotherPhone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MotherWorkPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MotherWorkPosition` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `HomePlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PostCode` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PoorGrade` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DetailNote` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`StudentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `userid`
--

DROP TABLE IF EXISTS `userid`;
CREATE TABLE `userid` (
  `QQ` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Class` tinyint NOT NULL,
  `Note` varchar(10) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`QQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping routines for database 'scu_cs_stu_info'
--

-- Dump completed on 2021-12-24 22:39:24
