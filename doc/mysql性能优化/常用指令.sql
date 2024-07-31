###事务隔离级别

- 查看当前事务隔离级别
select @@transaction_isolation;
或者
select @@tx_isolation;

- 设置当前会话事务隔离级别
-- 可重复读
set transaction_isolation="repeatable-read";
或者
-- 读提交
set tx_isolation="read-committed";


### 事务状态

-- 查询当前活跃的事务
select * from information_schema.INNODB_TRX;

-- 拥有锁或者等待锁的事务
select * from information_schema.INNODB_LOCKS;

-- 等待锁的事务
select * from information_schema.INNODB_LOCK_WAITS;


### innodb行锁争夺情况
show status like 'innodb_row_lock%'

Innodb_row_lock_current_waits  #正在等待锁的数量
Innodb_row_lock_time_avg
Innodb_row_lock_time_max       #等待锁最大时间
Innodb_row_lock_waits          #累计等待锁的数量


### innodb运行状况（死锁详情）

show engine innodb status


