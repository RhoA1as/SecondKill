package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.bean.SecKill;
import org.seckill.cache.RedisDao;
import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.enums.SecKillState;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKillDao successKillDao;
    //md5盐值字符串，用于混淆md5
    private static final String slat = "wdwqd74f234fsfsvg@#!sddd";

    @Autowired
    private RedisDao redisDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<SecKill> getSecKillList() {
        return secKillDao.queryAll(0, 4);
    }

    @Override
    public SecKill getById(long secKillId) {
        return secKillDao.queryById(secKillId);
    }

    @Override
    public Exposer exportSecKillURL(long secKillId) {
        SecKill secKill = null;
        secKill = redisDao.getSecKill(secKillId);
        if (secKill == null) {
            secKill = secKillDao.queryById(secKillId);
        }
        if (secKill == null) {
            return new Exposer(false, secKillId);
        }
        redisDao.putSecKill(secKill);
        long start_time = secKill.getStart_time().getTime();
        long end_time = secKill.getEnd_time().getTime();
        long now = new Date().getTime();
        if (now < start_time || now > end_time) {
            return new Exposer(false, now, start_time, end_time, secKillId);
        }
        //字符串加密，不可逆
        String md5 = getMD5(secKillId);
        return new Exposer(true, md5, secKillId);
    }

    @Override
    @Transactional
    public SecKillExecution executeSecKill(long secKillId, long userPhone, String md5)
            throws SecKillException, SecKillCloseException, RepeatKillException {
        try {
            if (md5 == null || !md5.equals(getMD5(secKillId))) {
                throw new SecKillException("secKill rewrite");
            }
            //记录购买行为
            int insert = successKillDao.insert(secKillId, userPhone);
            if (insert <= 0) {
                throw new RepeatKillException("secKill repeated");
            }
            //减库存
            int reduceStock = secKillDao.reduceStock(secKillId, new Date());
            if (reduceStock <= 0) {
                throw new SecKillCloseException("secKill closed");
            }
            return new SecKillExecution(secKillId, SecKillState.SUCCESS, successKillDao.queryById(secKillId, userPhone));
        } catch (SecKillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SecKillException("secKill inner error" + e.getMessage());
        }
    }

    @Override
    public SecKillExecution executeSecKillProcedure(long secKillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(secKillId))) {
            return new SecKillExecution(secKillId, SecKillState.DATA_REWRITE);
        }
        Date date = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("secKillId", secKillId);
        map.put("phone", userPhone);
        map.put("killTime", new Date());
        map.put("result", null);
        try {
            secKillDao.killByProcedure(map);
            Integer result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                return new SecKillExecution(secKillId, SecKillState.SUCCESS, successKillDao.queryById(secKillId, userPhone));
            } else {
                return new SecKillExecution(secKillId, SecKillState.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SecKillExecution(secKillId, SecKillState.INNER_ERROR);
        }
    }

    /**
     * 生成md5
     *
     * @return
     */
    private String getMD5(long secKillId) {
        String base = secKillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
