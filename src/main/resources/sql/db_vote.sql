/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50721
Source Host           : 47.100.113.55:3306
Source Database       : db_vote

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-05-19 13:20:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '用户登录名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `email` varchar(128) NOT NULL COMMENT '用户邮箱, 可以用作登录',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称, 初始和username一样',
  `headImage` varchar(256) DEFAULT NULL COMMENT '用户头像, 不设置使用默认头像',
  `desc` varchar(256) DEFAULT NULL COMMENT '用户简介',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '用户类型 0. 系统管理员, 1. 平台注册用户',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '用户状态 0. 正常 1. 冻结(不能发起和参与投票)  2. 删除',
  `sex` varchar(10) NOT NULL DEFAULT '未知',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间, 不能被修改',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('2', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1270458214@qq.com', 'ride', '/upload/head/0fdc25775815407296e6af8c0b6bf513.jpg', null, '0', '0', '未知', '2018-05-08 15:19:30');
INSERT INTO `t_user` VALUES ('3', '唐秀娟', '877a6319be474cb6680459896727652d', '1943418084@qq.com', '唐秀娟', '\\upload\\head\\default-head.jpg', null, '1', '0', '未知', '2018-05-08 15:56:03');

-- ----------------------------
-- Table structure for `t_user_login_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login_info`;
CREATE TABLE `t_user_login_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int(11) NOT NULL COMMENT '外键, 对应用户表主键',
  `loginTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `loginIp` varchar(32) NOT NULL COMMENT '登录IP地址',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `t_user_login_info_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_login_info
-- ----------------------------
INSERT INTO `t_user_login_info` VALUES ('1', '2', '2018-05-08 15:44:04', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('2', '3', '2018-05-08 15:56:31', '192.168.1.102');
INSERT INTO `t_user_login_info` VALUES ('3', '2', '2018-05-08 17:13:30', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('4', '2', '2018-05-08 19:21:15', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('5', '2', '2018-05-08 20:22:22', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('6', '2', '2018-05-08 20:30:11', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('7', '2', '2018-05-08 20:40:16', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('8', '2', '2018-05-08 20:42:31', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('9', '2', '2018-05-08 20:44:43', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('10', '2', '2018-05-08 21:05:34', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('11', '2', '2018-05-08 21:09:41', '127.0.0.1');
INSERT INTO `t_user_login_info` VALUES ('12', '2', '2018-05-12 15:42:07', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('13', '2', '2018-05-12 15:42:55', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('14', '2', '2018-05-16 19:53:42', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('15', '2', '2018-05-16 20:06:33', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('16', '2', '2018-05-19 12:30:00', '60.12.210.99');
INSERT INTO `t_user_login_info` VALUES ('17', '2', '2018-05-19 12:59:05', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('18', '2', '2018-05-19 13:03:37', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('19', '2', '2018-05-19 13:05:39', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user_login_info` VALUES ('20', '2', '2018-05-19 13:11:39', '192.168.1.103');
INSERT INTO `t_user_login_info` VALUES ('21', '2', '2018-05-19 13:13:15', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `t_user_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_message`;
CREATE TABLE `t_user_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int(11) NOT NULL COMMENT '外键, 对应用户表主键',
  `theme` varchar(128) NOT NULL COMMENT '消息标题',
  `message` text NOT NULL COMMENT '标题内容',
  `isRead` int(1) NOT NULL DEFAULT '0' COMMENT '是否已读 0. 未读   1. 已读',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `t_user_message_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_message
-- ----------------------------

-- ----------------------------
-- Table structure for `t_vote_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_vote_detail`;
CREATE TABLE `t_vote_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `itemId` int(11) NOT NULL COMMENT '外键, 对应条目表主键',
  `userId` int(11) DEFAULT NULL COMMENT '外键, 对应用户表主键',
  `openId` varchar(64) DEFAULT NULL COMMENT '微信openId',
  `ipAddress` varchar(32) NOT NULL COMMENT '投票者ip地址',
  `voteTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投票时间',
  PRIMARY KEY (`id`),
  KEY `itemId` (`itemId`),
  KEY `userId` (`userId`),
  CONSTRAINT `t_vote_detail_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `t_vote_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_vote_detail_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vote_detail
-- ----------------------------
INSERT INTO `t_vote_detail` VALUES ('2', '5', null, null, '127.0.0.1', '2018-05-08 21:17:50');
INSERT INTO `t_vote_detail` VALUES ('3', '8', '2', null, '60.12.210.99', '2018-05-19 12:32:45');
INSERT INTO `t_vote_detail` VALUES ('4', '12', '2', null, '192.168.1.103', '2018-05-19 13:12:24');

-- ----------------------------
-- Table structure for `t_vote_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_vote_item`;
CREATE TABLE `t_vote_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `themeId` int(11) NOT NULL COMMENT '外键, 对应主题表中的主键',
  `title` varchar(128) NOT NULL COMMENT '条目简介',
  `detail` text NOT NULL COMMENT '条目详细',
  `photo` varchar(256) NOT NULL COMMENT '图片1 作为封面',
  `photo2` varchar(256) DEFAULT NULL COMMENT '图片2',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间, 不能修改',
  PRIMARY KEY (`id`),
  KEY `themeId` (`themeId`),
  CONSTRAINT `t_vote_item_ibfk_1` FOREIGN KEY (`themeId`) REFERENCES `t_vote_theme` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vote_item
-- ----------------------------
INSERT INTO `t_vote_item` VALUES ('5', '7', '1', '1', '\\upload\\photo\\aefe744ad8fe4d05a8a5ff75eca77123.jpg', null, '2018-05-08 21:11:23');
INSERT INTO `t_vote_item` VALUES ('6', '7', '2', '2', '\\upload\\photo\\24319d104fbd43868c9d69c80a234f31.jpg', null, '2018-05-08 21:11:32');
INSERT INTO `t_vote_item` VALUES ('7', '8', '11', '11', '/upload/photo/26e0366ce7c44ec18d982e66fec08d04.jpg', null, '2018-05-19 12:30:58');
INSERT INTO `t_vote_item` VALUES ('8', '8', '22', '22', '/upload/photo/b093ca5c657c4c409b8a370668cd4fcb.jpg', null, '2018-05-19 12:31:06');
INSERT INTO `t_vote_item` VALUES ('9', '8', '33', '33', '/upload/photo/e6b056d8e7e346e7acf4e88282689276.jpg', null, '2018-05-19 12:31:20');
INSERT INTO `t_vote_item` VALUES ('10', '9', '2', '2', '/upload/photo/bc72dd7a739a4ff786a1ca19d5d44465.jpg', null, '2018-05-19 12:33:48');
INSERT INTO `t_vote_item` VALUES ('11', '9', '3', '3', '/upload/photo/782909844c824bbd818aaca5eed48e08.jpg', null, '2018-05-19 12:33:58');
INSERT INTO `t_vote_item` VALUES ('12', '10', '11', '11', '\\upload\\photo\\92d3c692488749de818f795c8e67ef47.jpg', null, '2018-05-19 13:07:15');

-- ----------------------------
-- Table structure for `t_vote_player`
-- ----------------------------
DROP TABLE IF EXISTS `t_vote_player`;
CREATE TABLE `t_vote_player` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `themeId` int(11) NOT NULL COMMENT '外键, 对应主题表主键',
  `itemId` int(11) DEFAULT NULL COMMENT '外键, 对应条目表主键',
  `name` varchar(32) NOT NULL COMMENT '选手姓名',
  `phone` varchar(11) NOT NULL COMMENT '选手手机号',
  `email` varchar(64) NOT NULL COMMENT '选手邮箱',
  `title` varchar(128) NOT NULL COMMENT '条目简介',
  `detail` text NOT NULL COMMENT '条目详细',
  `photo` varchar(256) NOT NULL COMMENT '照片1, 作为封面',
  `photo2` varchar(256) DEFAULT NULL COMMENT '照片2',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  `occupation` varchar(32) DEFAULT NULL COMMENT '职业',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '审核状态 0. 审核中  1. 审核通过  2. 审核不通过',
  PRIMARY KEY (`id`),
  KEY `themeId` (`themeId`),
  KEY `itemId` (`itemId`),
  CONSTRAINT `t_vote_player_ibfk_1` FOREIGN KEY (`themeId`) REFERENCES `t_vote_theme` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_vote_player_ibfk_2` FOREIGN KEY (`itemId`) REFERENCES `t_vote_item` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vote_player
-- ----------------------------

-- ----------------------------
-- Table structure for `t_vote_theme`
-- ----------------------------
DROP TABLE IF EXISTS `t_vote_theme`;
CREATE TABLE `t_vote_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int(11) NOT NULL COMMENT '外键, 投票发起者, 对应用户表主键',
  `theme` varchar(64) NOT NULL COMMENT '投票主题',
  `desc` text COMMENT '投票描述',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投票创建时间',
  `startTime` datetime NOT NULL COMMENT '投票开始时间',
  `endTime` datetime NOT NULL COMMENT '投票结束时间',
  `isSingle` int(1) NOT NULL DEFAULT '0' COMMENT '是否为单选 0. 单选   1. 多选',
  `maxSelect` int(4) DEFAULT '3' COMMENT '当选择多选时起作用',
  `isAnonymous` int(1) DEFAULT '0',
  `timeDiff` int(4) NOT NULL DEFAULT '24' COMMENT '是否允许匿名投票 0. 允许  1. 不允许  2. 微信投票',
  `isRestrictedZone` int(1) DEFAULT '0' COMMENT '是否限制投票区域 0. 不限制   1. 限制',
  `ipMax` int(11) NOT NULL DEFAULT '-1' COMMENT '每个IP的最大投票数, 如果为-1则不限制',
  `region` varchar(10) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '投票状态 0. 未开始, 1. 进行中, 2. 已结束',
  `city` varchar(10) DEFAULT NULL,
  `isIPRestriction` int(2) DEFAULT '0',
  `endIp` varchar(64) DEFAULT NULL,
  `startIp` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `t_vote_theme_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vote_theme
-- ----------------------------
INSERT INTO `t_vote_theme` VALUES ('6', '2', '1', '1', '2018-05-08 21:10:33', '2018-05-08 21:10:30', '2018-05-09 00:00:00', '0', '0', '2', '24', '1', '1', '青海', '0', '黄南', '0', null, null);
INSERT INTO `t_vote_theme` VALUES ('7', '2', '12', '12', '2018-05-08 21:11:14', '2018-05-08 21:15:00', '2018-05-10 00:00:00', '0', '0', '0', '24', '1', '12', '浙江', '0', '宁波', '0', null, null);
INSERT INTO `t_vote_theme` VALUES ('8', '2', '1', '1', '2018-05-19 12:30:38', '2018-05-19 12:32:00', '2018-05-20 00:00:00', '0', '0', '0', '24', '1', '10', '浙江', '0', '宁波', '0', null, null);
INSERT INTO `t_vote_theme` VALUES ('9', '2', '其他地区', '啊啊啊', '2018-05-19 12:33:39', '2018-05-19 12:34:32', '2018-05-21 00:00:00', '0', '0', '0', '24', '1', '12', '宁夏', '0', '石嘴山', '0', null, null);
INSERT INTO `t_vote_theme` VALUES ('10', '2', '11', '111', '2018-05-19 13:07:02', '2018-05-19 13:07:00', '2018-05-21 00:00:00', '0', '0', '0', '24', '0', '11', null, '0', null, '1', '192.168.1.254', '192.168.1.1');

-- ----------------------------
-- Table structure for `t_wechat_follow`
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat_follow`;
CREATE TABLE `t_wechat_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(64) NOT NULL COMMENT '微信号关注的openId',
  `followTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wechat_follow
-- ----------------------------
