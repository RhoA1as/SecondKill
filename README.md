# SecondKill
基于maven,SSM,redis实现的一个简单秒杀项目
# 1.Dao层
  ## 1.1建表
  - 商品详情表 商品id 商品名 秒杀开启时间 秒杀结束时间 商品记录创建时间
  - 订单表 商品id 秒杀手机号 商品状态 订单创建时间
  ## 1.2 Entity
  - Seckill
  - SuccessKilled
  ## 1.3 SeckillDao
  ``` 
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
}
  ```
  ## 1.4 SuccessKillDao
  ```
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
  ```
  
