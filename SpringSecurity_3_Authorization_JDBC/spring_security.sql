/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : spring_security

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-10-08 09:45:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `authority` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  UNIQUE KEY `ix_authorities_username` (`username`,`authority`) USING BTREE,
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `authorities` VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO `authorities` VALUES ('admin', 'ROLE_USER');
INSERT INTO `authorities` VALUES ('user', 'ROLE_USER');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `enabled` tinyint(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('admin', '123', '1');
INSERT INTO `users` VALUES ('user', '123', '1');
