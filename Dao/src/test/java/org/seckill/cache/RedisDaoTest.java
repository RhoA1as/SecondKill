package org.seckill.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.bean.SecKill;
import org.seckill.dao.SecKillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring_dao.xml")
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SecKillDao secKillDao;
    @Test
    public void getSecKill() {
        System.out.println(redisDao.getSecKill(1000));
    }

    @Test
    public void putSecKill() {
        SecKill secKill = secKillDao.queryById(1000);
        System.out.println(redisDao.putSecKill(secKill));
    }
}