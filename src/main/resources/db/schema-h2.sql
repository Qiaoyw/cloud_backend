DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	username VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	password INT(11) NULL DEFAULT NULL COMMENT '密码',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (email)
);