create table tb_staff (
	id BIGINT NOT NULL COMMENT 'ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	version INT NOT NULL DEFAULT 0 COMMENT '版本号',
	deleted VARCHAR(10) NOT NULL DEFAULT 'NO' COMMENT '删除状态',
    account VARCHAR(50) NOT NULL COMMENT '源数据ID',
    password VARCHAR(60) NOT NULL COMMENT '密码',
    state VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'  COMMENT '是否启用',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='媒体资源表';