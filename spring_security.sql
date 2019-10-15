/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : spring_security

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-10-15 14:31:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `series` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户是否可用',
  `roles` text CHARACTER SET utf8mb4 COMMENT '用户角色，多个角色之间使用逗号隔开',
  PRIMARY KEY (`id`),
  KEY `idx_users_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '$2a$12$ElpqwmB0ih0UXzw7kWdYRepTizSV2Ogb3key8LRAiehZ7gkbGaaU.', '1', 'ROLE_ADMIN,ROLE_USER');
INSERT INTO `users` VALUES ('2', 'user', '123', '1', 'ROLE_USER');
