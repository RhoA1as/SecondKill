create database seckill;
use seckill;
/*创建秒杀库存表*/
create table seckill_stock(
 seckill_id bigint not null auto_increment comment '商品库存id',
 name varchar(120) not null comment '商品名称',
 number int not null comment '库存数量',
 start_time timestamp not null comment '秒杀开启时间',
 end_time timestamp not null comment '秒杀结束时间',
 create_time timestamp not null default current_timestamp comment '创建时间',
 PRIMARY KEY(seckill_id),
 key idx_start_time(start_time),
 key idx_end_time(end_time),
 key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8  COMMENT='秒杀库存表';

insert into seckill_stock(name,number,start_time,end_time) values
('1元秒杀iphone12',100,'2021-11-10 00:00:00','2021-11-10 00:10:00'),
('1元秒杀ipad2020',50,'2021-11-11 00:00:00','2021-11-11 00:10:00'),
('10元秒杀macbook',10,'2021-11-12 00:00:00','2021-11-12 00:10:00'),
('100元秒杀macpro',5,'2021-11-13 00:00:00','2021-11-13 00:10:00');

/*秒杀成功明细表
  用户登录认证相关信息
*/
create table success_seckilled(
    seckill_id bigint not null comment '商品库存id',
    user_phone bigint not null comment '用户预约手机号',
    state tinyint not null default -1 comment '状态 -1无效 0有效 1已付款 2已发货',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key(seckill_id,user_phone),
    key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='秒杀成功明细表';