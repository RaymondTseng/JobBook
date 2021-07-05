package com.example.jobbook.model.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2017/1/26.
 */

public class UmengMessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int type;

    private String accountFrom;

    private String accountTo;

    private String event;

    private String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
