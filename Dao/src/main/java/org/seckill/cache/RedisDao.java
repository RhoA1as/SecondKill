package org.seckill.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.bean.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

    private JedisPool jedisPool;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public SecKill getSecKill(long secKillId) {
        Jedis jedis = null;
        SecKill secKill = null;
        try {
            jedis = jedisPool.getResource();
            String key = "secKill:"+secKillId;
            //redis不支持内部序列化
            //采用自定义序列化的方式
            byte[] bytes = jedis.get(key.getBytes());
            if(bytes != null){
                secKill = schema.newMessage();//空对象
                ProtostuffIOUtil.mergeFrom(bytes,secKill,schema);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return secKill;
    }

    public String putSecKill(SecKill secKill) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            String key = "secKill:" + secKill.getSecKill_id();
            byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            result = jedis.setex(key.getBytes(), 60 * 60, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }
}
