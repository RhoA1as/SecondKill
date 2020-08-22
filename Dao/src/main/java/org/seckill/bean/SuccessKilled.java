package org.seckill.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class SuccessKilled implements Serializable {
    private static final long serialVersionUID = -3528195121178533615L;
    private long secKill_id;
    private long user_phone;
    private Integer state;
    private Date create_time;
    private SecKill secKill;

    public long getSecKill_id() {
        return secKill_id;
    }

    public void setSecKill_id(long secKill_id) {
        this.secKill_id = secKill_id;
    }

    public long getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(long user_phone) {
        this.user_phone = user_phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "secKill_id=" + secKill_id +
                ", user_phone=" + user_phone +
                ", state=" + state +
                ", create_time=" + create_time +
                ", secKill=" + secKill +
                '}';
    }
}
