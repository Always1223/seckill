-- 数据库初始化脚本
DROP TABLE seckill;
DROP TABLE success_killed;
-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` INT NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time (start_time),
key idx_end_time (end_time),
key idx_create_time (create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
insert into
    seckill(name,number,start_time,end_time)
values
    ('1000元秒杀iphone6',100,'2019-07-01 00:00:00','2019-11-12 00:00:00'),
    ('500元秒杀ipad2',200,'2019-07-01 00:00:00','2019-07-02 00:00:00'),
    ('300元秒杀小米4',300,'2019-07-05 00:00:00','2019-11-12 00:00:00'),
    ('200元秒杀红米note',400,'2019-11-01 00:00:00','2019-11-12 00:00:00');

-- 创建秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL COMMENT '商品库存id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效 0：成功 1：已付款 2：已发货',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id, user_phone), /* 联合主键 */
key idx_create_time (create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

# -- 连接数据库控制台
# mysql -uroot -p
#
# -- 为什么手写DDL
# -- 记录每次上线的DDL修改
# -- 上线V1.1
# alter table seckill
# drop index idx_create_time,
# add index idx_c_s(start_time, create_time);

-- 上线V1.2
-- 新的DDL