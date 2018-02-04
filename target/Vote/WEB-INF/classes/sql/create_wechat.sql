CREATE TABLE IF NOT EXISTS t_wechat_follow (
  `id`         INT      AUTO_INCREMENT,
  `openId`     VARCHAR(64) NOT NULL
  COMMENT '微信号关注的openId',
  `followTime` DATETIME DEFAULT CURRENT_TIMESTAMP
  COMMENT '关注时间',
  PRIMARY KEY (`id`)
)
  ENGINE InnoDB
  CHARSET UTF8;