create table tb_block (
	id BIGINT NOT NULL COMMENT 'ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	version INT NOT NULL DEFAULT 0 COMMENT '版本号',
	deleted VARCHAR(10) NOT NULL DEFAULT 'NO' COMMENT '删除状态',
	from_user_id BIGINT NOT NULL COMMENT '用户Id',
	to_user_id BIGINT NOT NULL COMMENT '拉黑用户id',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='拉黑表';