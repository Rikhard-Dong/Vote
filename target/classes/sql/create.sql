# 注册用户表
CREATE TABLE IF NOT EXISTS t_user (
  `id`         INT(11)                       AUTO_INCREMENT
  COMMENT '主键',
  `username`   VARCHAR(32)  NOT NULL UNIQUE
  COMMENT '用户登录名',
  `password`   VARCHAR(32)  NOT NULL
  COMMENT '用户密码',
  `email`      VARCHAR(128) NOT NULL UNIQUE
  COMMENT '用户邮箱, 可以用作登录',
  `nickname`   VARCHAR(32)  NULL             DEFAULT NULL
  COMMENT '用户昵称, 初始和username一样',
  `headImage`  VARCHAR(256) NULL             DEFAULT NULL
  COMMENT '用户头像, 不设置使用默认头像',
  `desc`       VARCHAR(256) NULL             DEFAULT NULL
  COMMENT '用户简介',
  `type`       INT(1)       NOT NULL         DEFAULT '1'
  COMMENT '用户类型 0. 系统管理员, 1. 平台注册用户',
  `status`     INT(1)       NOT NULL         DEFAULT '0'
  COMMENT '用户状态 0. 正常 1. 冻结(不能发起和参与投票)  2. 删除',
  `sex`        VARCHAR(10)  NOT NULL         DEFAULT '未知',
  `createTime` DATETIME     NOT NULL         DEFAULT current_timestamp
  COMMENT '用户注册时间, 不能被修改',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  CHARSET utf8;

DELETE FROM t_user;
INSERT INTO t_user (username, password, email, nickname, type, headImage)
VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', '1270458214@qq.com', 'ride', '0', '/upload/head/default-head.jpg');

#  用户登录信息表
CREATE TABLE IF NOT EXISTS t_user_login_info (
  `id`        INT(11)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             AUTO_INCREMENT
  COMMENT '主键',
  `userId`    INT(11)     NOT NULL
  COMMENT '外键, 对应用户表主键',
  `loginTime` DATETIME NOT NULL                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              DEFAULT current_timestamp
  COMMENT '登录时间',
  `loginIp`   VARCHAR(32) NOT NULL
  COMMENT '登录IP地址',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  CHARSET utf8;

# 用户消息表
CREATE TABLE IF NOT EXISTS t_user_message (
  `id`      INT(11)              AUTO_INCREMENT
  COMMENT '主键',
  `userId` INT(11)      NOT NULL
  COMMENT '外键, 对应用户表主键',
  `theme`  VARCHAR(128) NOT NULL
  COMMENT '消息标题',
  `message` TEXT        NOT NULL
  COMMENT '标题内容',
  `isRead`  INT(1)      NOT NULL DEFAULT '0'
  COMMENT '是否已读 0. 未读   1. 已读',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  CHARSET utf8;

# 投票主题表
CREATE TABLE IF NOT EXISTS t_vote_theme (
  `id`               INT(11)            AUTO_INCREMENT
  COMMENT '主键',
  `userId` INT(11)           NOT NULL
  COMMENT '外键, 投票发起者, 对应用户表主键',
  `theme`  VARCHAR(64) NOT NULL
  COMMENT '投票主题',
  `desc`   TEXT COMMENT '投票描述',
  `createTime` DATETIME NOT NULL DEFAULT current_timestamp
  COMMENT '投票创建时间',
  `startTime`  DATETIME NOT NULL
  COMMENT '投票开始时间',
  `endTime`    DATETIME NOT NULL
  COMMENT '投票结束时间',
  `isSingle`   INT(1)   NOT NULL DEFAULT '0'
  COMMENT '是否为单选 0. 单选   1. 多选',
  `maxSelect`  INT(4)            DEFAULT '3'
  COMMENT '当选择多选时起作用',
  `isAnonymous` INT(1)            DEFAULT '0',
  `timeDiff`    INT(4)  NOT NULL  DEFAULT '24'
  COMMENT '时间差, 下一次投票需要多久之后, 默认24小时'
  COMMENT '是否允许匿名投票 0. 允许  1. 不允许  2. 微信投票',
  `isRestrictedZone` INT(1)            DEFAULT '0'
  COMMENT '是否限制投票区域 0. 不限制   1. 限制',
  `ipZone`           VARCHAR(32) COMMENT 'IP地址所属的区域',
  `ipMax`            INTEGER NOT NULL  DEFAULT '-1'
  COMMENT '每个IP的最大投票数, 如果为-1则不限制',
  `status`           INT(1)  NOT NULL   DEFAULT '0'
  COMMENT '投票状态 0. 未开始, 1. 进行中, 2. 已结束',

  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  CHARSET utf8;

# 投票条目表
CREATE TABLE IF NOT EXISTS t_vote_item (
  `id`         INT(11)            AUTO_INCREMENT
  COMMENT '主键',
  `themeId` INT(11)      NOT NULL
  COMMENT '外键, 对应主题表中的主键',
  `title`   VARCHAR(128) NOT NULL
  COMMENT '条目简介',
  `detail`  TEXT         NOT NULL
  COMMENT '条目详细',
  `photo`   VARCHAR(256) NOT NULL
  COMMENT '图片1 作为封面',
  `photo2`  VARCHAR(256) COMMENT '图片2',
  `createTime` DATETIME  NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间, 不能修改',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`themeId`) REFERENCES `t_vote_theme` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  CHARSET utf8;

# 投票选手表
CREATE TABLE IF NOT EXISTS t_vote_player (
  `id`         INT(11)               AUTO_INCREMENT
  COMMENT '主键',
  `themeId` INT(11)         NOT NULL
  COMMENT '外键, 对应主题表主键',
  `itemId`  INT(11) NULL
  COMMENT '外键, 对应条目表主键',
  `name`    VARCHAR(32) NOT NULL
  COMMENT '选手姓名',
  `phone`   VARCHAR(11) NOT NULL
  COMMENT '选手手机号',
  `email`   VARCHAR(64) NOT NULL
  COMMENT '选手邮箱',
  `title`   VARCHAR(128) NOT NULL
  COMMENT '条目简介',
  `detail`  TEXT         NOT NULL
  COMMENT '条目详细',
  `photo`   VARCHAR(256) NOT NULL
  COMMENT '照片1, 作为封面',
  `photo2`  VARCHAR(256) NULL DEFAULT NULL
  COMMENT '照片2',
  `sex`     VARCHAR(10)  NULL DEFAULT NULL
  COMMENT '性别',
  `age`     INT(4)       NULL DEFAULT NULL
  COMMENT '年龄',
  `occupation` VARCHAR(32) NULL  DEFAULT NULL
  COMMENT '职业',
  `address`    VARCHAR(128) NULL DEFAULT NULL
  COMMENT '地址',
  `createTime` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `status`     INT(1)       NOT NULL DEFAULT '0'
  COMMENT '审核状态 0. 审核中  1. 审核通过  2. 审核不通过',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`themeId`) REFERENCES `t_vote_theme` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (`itemId`) REFERENCES `t_vote_item` (`id`)
    ON UPDATE SET NULL
    ON DELETE SET NULL
)
  ENGINE = InnoDB
  CHARSET utf8;

# 投票详情表
CREATE TABLE IF NOT EXISTS t_vote_detail (
  `id`        INT(11)              AUTO_INCREMENT
  COMMENT '主键',
  `itemId` INT(11)        NOT NULL
  COMMENT '外键, 对应条目表主键',
  `userId` INT(11) NULL DEFAULT NULL
  COMMENT '外键, 对应用户表主键',
  `openId` VARCHAR(64) NULL
  COMMENT '微信openId',
  `ipAddress` VARCHAR(32) NOT NULL
  COMMENT '投票者ip地址',
  `voteTime`  DATETIME    NOT NULL DEFAULT current_timestamp
  COMMENT '投票时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`itemId`) REFERENCES `t_vote_item` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
    ON UPDATE SET NULL
    ON DELETE SET NULL
)
  ENGINE = InnoDB
  CHARSET utf8;