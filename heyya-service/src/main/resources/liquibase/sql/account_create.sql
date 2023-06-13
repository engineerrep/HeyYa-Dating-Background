create table tb_account (
	id BIGINT NOT NULL COMMENT 'ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	version INT NOT NULL DEFAULT 0 COMMENT '版本号',
	deleted VARCHAR(10) NOT NULL DEFAULT 'NO' COMMENT '删除状态',
	account VARCHAR(500) NOT NULL COMMENT '账号',
    user_id BIGINT NOT NULL COMMENT '用户id',
    type VARCHAR(20) NOT NULL DEFAULT 'APPLEID' COMMENT '注册方式',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户账号表';