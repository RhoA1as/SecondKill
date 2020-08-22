package org.seckill.bean;

import java.io.Serializable;
import java.util.Date;

public class SecKill implements Serializable {
    private static final long serialVersionUID = 3056640658014772105L;
    private Long secKill_id;
    private String name;
    private Integer number;
    private Date start_time;
    private Date end_time;
    private Date create_time;

    public Long getSecKill_id() {
        return secKill_id;
    }

    public void setSecKill_id(Long secKill_id) {
        this.secKill_id = secKill_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "SecKill{" +
                "secKill_id=" + secKill_id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", create_time=" + create_time +
                '}';
    }
}
