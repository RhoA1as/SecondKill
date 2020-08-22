package org.seckill.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.seckill.bean.SecKill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 库存修改
 */
public interface SecKillDao {
    /**
     * 减少库存
     * @param secKillId
     * @param killTime
     * @return
     */
    @Update("update seckill_stock set number = number-1 where seckill_id = #{id} and (#{time} between start_time and end_time) and number > 0 ")
    int reduceStock(@Param("id") long secKillId, @Param("time") Date killTime);

    /**
     * 根据id查询
     * @param secKillId
     * @return
     */
    @Select("select * from seckill_stock where seckill_id = #{secKillId}")
    SecKill queryById(long secKillId);

    /**
     * 根据偏移量查询所有秒杀
     * @return
     */
    @Select("select * from seckill_stock order by create_time desc limit #{offSet},#{limit}")
    List<SecKill> queryAll(@Param("offSet") int offSet,@Param("limit") int limit);

    @Select("call execute_secKill(#{secKillId,jdbcType=BIGINT,mode=IN}," +
            "#{phone,jdbcType=BIGINT,mode=IN}," +
            "#{killTime,jdbcType=TIMESTAMP,mode=IN}," +
            "#{result,jdbcType=INTEGER,mode=OUT})")
    @Options(statementType= StatementType.CALLABLE)
    void killByProcedure(Map<String,Object> params);

}
