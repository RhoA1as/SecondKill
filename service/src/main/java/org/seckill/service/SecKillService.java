package org.seckill.service;

import org.seckill.bean.SecKill;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;

import java.util.List;

public interface SecKillService {
    /**
     * 显示秒杀列表
     * @return
     */
    List<SecKill> getSecKillList();

    /**
     * 根据id查询
     * @param secKillId
     * @return
     */
    SecKill getById(long secKillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     * @param secKillId
     */
    Exposer exportSecKillURL(long secKillId);

    /**
     * 执行秒杀，md5进行验证
     * @param secKillId
     * @param userPhone
     * @param md5
     */
    SecKillExecution executeSecKill(long secKillId, long userPhone, String md5)
            throws SecKillException, SecKillCloseException, RepeatKillException;

    /**
     * 通过存储过程
     * @param secKillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws SecKillCloseException
     * @throws RepeatKillException
     */
    SecKillExecution executeSecKillProcedure(long secKillId, long userPhone, String md5);
}
