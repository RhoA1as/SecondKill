DELIMITER $$
--定义存储过程
--参数：in 输入参数; out 输出参数
--row_count()返回上一条修改sql影响行数
CREATE PROCEDURE `seckill`.`execute_seckill`(
  IN v_seckill_id BIGINT ,
  IN v_phone BIGINT ,
  IN v_kill_time TIMESTAMP ,
  OUT r_result INT
)
  BEGIN
    -- insert_count 临时变量定义(需要在事物开始之前定义）
    DECLARE insert_count INT DEFAULT 0 ;
    START TRANSACTION ;
    INSERT IGNORE INTO success_seckilled(
    seckill_id ,
    user_phone ,
    state
    ) VALUES (
      v_seckill_id ,
      v_phone ,
      0
    ) ;
    -- row_count():返回上一条修改类型sql(delete,insert,upodate)的影响行数
    -- row_count: 0:未修改数据; >0:表示修改的行数; <0:sql错误/未执行修改sql
    SELECT ROW_COUNT() INTO insert_count ;
    IF (insert_count = 0) THEN
      ROLLBACK ;
      -- 设置满足业务逻辑需要的返回结果 r_result
      SET r_result = - 1 ;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = - 2 ;
    ELSE
      UPDATE seckill_stock
      SET number = number - 1
      WHERE
        seckill_id = v_seckill_id
        AND end_time > v_kill_time
        AND start_time < v_kill_time
        AND number > 0 ;
      -- 再次使用 row_count()
      SELECT ROW_COUNT() INTO insert_count ;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = 0 ;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET r_result = - 2 ;
      ELSE
        COMMIT ;
        SET r_result = 1 ;
      END IF ;
    END IF ;
  END ;
$$
DELIMITER ;
set @r_result=-3;
call execute_secKill(1000,18796681905,now(),@r_result);
select @r_result;