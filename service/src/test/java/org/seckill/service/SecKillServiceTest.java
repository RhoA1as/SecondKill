package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml","classpath:spring/spring_service.xml"})
public class SecKillServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SecKillService secKillService;
    @Test
    public void getSecKillList() {
        System.out.println(secKillService.getSecKillList());
    }

    @Test
    public void getById() {
        System.out.println(secKillService.getById(1000));
    }

    @Test
    public void exportSecKillURL() {
        System.out.println(secKillService.exportSecKillURL(1000));
    }

    @Test
    public void executeSecKill() {
        Exposer exposer = secKillService.exportSecKillURL(1000);
        if (exposer.isExposed()) {
            try {
                System.out.println(secKillService.executeSecKill(1000, 15262049413L, exposer.getMd5()));
            } catch (RepeatKillException e) {
                logger.error(e.getMessage(),e);
            }catch (SecKillCloseException e){
                logger.error(e.getMessage(),e);
            }
        } else {
            logger.warn("exposer={}",exposer);
        }
    }
    @Test
    public void executeSecKillProcedure() {

        secKillService.executeSecKillProcedure(1000,18064806280L,"1d659690655e57802722efe8e1ae995a");
    }
}