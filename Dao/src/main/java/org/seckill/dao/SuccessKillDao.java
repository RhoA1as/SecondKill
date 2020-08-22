package org.seckill.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.seckill.bean.SuccessKilled;

import java.math.BigInteger;

public interface SuccessKillDao {
    /**
     * 插入购买明细，可过滤重复
     * @param secKillId
     * @param userPhone
     * @return
     *
     * 避免主键冲突报错 ignore
     */
    @Insert("insert ignore into success_seckilled(seckill_id,user_phone,state) values(#{id},#{phone},0)")
    int insert(@Param("id") long secKillId,@Param("phone") long userPhone);

    /**
     * 根据id查询
     * @param secKillId
     * @return
     */
    @Results(id = "successKilled",
     value = {@Result(id = true,column = "seckill_id",property = "secKill_id"),
              @Result(column = "user_phone",property = "user_phone"),
              @Result(column = "state",property = "state"),
              @Result(column = "create_time",property = "create_time"),
              @Result(column = "seckill_id",property = "secKill",
                      one = @One(select = "org.seckill.dao.SecKillDao.queryById",fetchType = FetchType.EAGER))
            }
    )
    //select s.* , ss.* from success_seckilled s join seckill_stock ss on ss.seckill_id = s.seckill_id where s.seckill_id = #{secKillId};
    @Select("select * from success_seckilled where seckill_id = #{secKillId} and user_phone = #{phone}")
    SuccessKilled queryById(@Param("secKillId") long secKillId,@Param("phone") long user_phone);
}
