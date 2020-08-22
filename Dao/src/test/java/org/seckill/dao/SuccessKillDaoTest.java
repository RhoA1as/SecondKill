package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring_dao.xml")


public class SuccessKillDaoTest {
    @Autowired
    SuccessKillDao successKillDao;

    @Test
    public void insert() {
        successKillDao.insert(1000,15262049413L);
    }

    @Test
    public void queryById() {
        System.out.println(successKillDao.queryById( 1000,15262049413L));
    }
}
