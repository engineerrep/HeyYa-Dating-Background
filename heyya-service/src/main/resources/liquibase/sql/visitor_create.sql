create table tb_visitor (
	id BIGINT NOT NULL COMMENT 'ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	version INT NOT NULL DEFAULT 0 COMMENT '版本号',
	deleted VARCHAR(10) NOT NULL DEFAULT 'NO' COMMENT '删除状态',
	from_user_id BIGINT NOT NULL COMMENT '访问者Id',
	to_user_id BIGINT NOT NULL COMMENT '被访问者Id',
	visit_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '拜访时间',
	PRIMARY KEY (id),
	UNIQUE KEY (from_user_id,to_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='访客表关系表';