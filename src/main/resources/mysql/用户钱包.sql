
CREATE TABLE `users_purse` (
  `uid` bigint(20) NOT NULL,
  `gold_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '普通金币',
  `noble_gold_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '贵族金币',
  `diamond_num` double(20,2) NOT NULL DEFAULT '0.00' COMMENT '钻石数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `state` int(11) DEFAULT '1' COMMENT '状态：1.可用 2.不可用',
  `diamond_state` int(11) DEFAULT '1' COMMENT '钻石状态 1.正常 2.冻结',
  `novice_gold` bigint(20) DEFAULT '0' COMMENT '新手金币',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户钱包';


CREATE TABLE `user_trade_record` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `trade_no` varchar(40) NOT NULL COMMENT '交易编号',
                                     `uid` bigint(20) NOT NULL COMMENT '用户uid',
                                     `num` bigint(20) NOT NULL COMMENT '交易数量',
                                     `source_id` int(11) NOT NULL COMMENT '业务来源id',
                                     `operate_type` tinyint(4) NOT NULL COMMENT '操作类型(1增加金币 2减少金币 3冲正金币)',
                                     `create_time` datetime NOT NULL COMMENT '创建时间',
                                     `update_time` datetime NOT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE KEY `idx_tradeno_sourceid` (`trade_no`,`source_id`) COMMENT '来源交易号唯一性索引',
                                     KEY `idx_create_time` (`create_time`) USING BTREE COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户交易记录表';


--- 导入数据存储过程

drop procedure if exists proc_initdata_users_purse;
create procedure proc_initdata_users_purse()

    begin

        declare i int default 0;
        declare diamond_num int default 0;
        declare gold_num int default 0;

        start transaction;

            while
                i <= 200000000 do
                select floor( 1 + ( rand( ) * 10000 ) ) into gold_num;
                select floor( 1 + ( rand( ) * 10000 ) ) into diamond_num;

                insert into users_purse(gold_num,noble_gold_num,diamond_num,create_time) value( gold_num, 0, diamond_num, now());

                set i = i + 1;
            end while;

        commit;

    end


---调用存储过程
call proc_initdata_users_purse()