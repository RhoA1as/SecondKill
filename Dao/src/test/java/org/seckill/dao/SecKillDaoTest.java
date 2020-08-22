package org.seckill.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring_dao.xml")
public class SecKillDaoTest {
    @Autowired
    SecKillDao secKillDao;
    @Test
    public void reduceStock() {
        System.out.println(secKillDao.reduceStock(1000, new Date()));
    }

    @Test
    public void queryById() {
        System.out.println(secKillDao.queryById(1001));
    }

    @Test
    public void queryAll() {
        System.out.println(secKillDao.queryAll(0, 10));
    }
}
