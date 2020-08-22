package org.seckill.exception;

import org.seckill.dto.SecKillExecution;

/**
 * 重复秒杀异常
 */
public class RepeatKillException extends SecKillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
