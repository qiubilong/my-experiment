CREATE TABLE `employees` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(24) NOT NULL DEFAULT '' COMMENT '姓名',
 `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
 `position` varchar(20) NOT NULL DEFAULT '' COMMENT '职位',
 `hire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入职时间',
   PRIMARY KEY (`id`),
   KEY `idx_name_age_position` (`name`,`age`,`position`) USING BTREE
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='员工记录表';

 INSERT INTO employees(name,age,position,hire_time) VALUES('LiLei',22,'manager',NOW());
 INSERT INTO employees(name,age,position,hire_time) VALUES('HanMeimei', 23,'dev',NOW());
 INSERT INTO employees(name,age,position,hire_time) VALUES('Lucy',23,'dev',NOW());

--生成数据的存储过程
drop procedure if exists insert_emp;
create procedure insert_emp()
begin
	 declare chars_str varchar(100) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
	 declare i int default 1;
	 while(i<=100000) do
		insert into employees(name,age,position) values(substring(chars_str , FLOOR(1 + RAND()*62 ),4),i,'dev');
		set i = i+1;
    end while;
end
--生成数据的存储过程-调用
call insert_emp()


//白名单表
CREATE TABLE `moment_dynamic_whitelist` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) DEFAULT NULL,
    `state` int(11) DEFAULT '2' COMMENT '2=图文，3=视频',
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='动态白名单';



--分析语句
explain
delete from moment_dynamic_whitelist where id in(
    select temp.id from(SELECT id  FROM `moment_dynamic_whitelist` group by `user_id`  having count( `user_id`)>1) temp
)