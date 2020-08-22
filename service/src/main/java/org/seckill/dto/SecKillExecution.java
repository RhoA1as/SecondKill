package org.seckill.dto;

import org.seckill.bean.SuccessKilled;
import org.seckill.enums.SecKillState;

/**
 * 封装秒杀执行后结果
 */
public class SecKillExecution {

    private long secKillId;
    //秒杀状态
    private int state;
    //状态描述
    private String stateInfo;

    private SuccessKilled successKilled;

    public SecKillExecution(long secKillId, SecKillState secKillState, SuccessKilled successKilled) {
        this.secKillId = secKillId;
        this.state = secKillState.getState();
        this.stateInfo = secKillState.getStateInfo();
        this.successKilled = successKilled;
    }

    public SecKillExecution(long secKillId, SecKillState secKillState) {
        this.secKillId = secKillId;
        this.state = secKillState.getState();
        this.stateInfo = secKillState.getStateInfo();
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SecKillExecution{" +
                "secKillId=" + secKillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
