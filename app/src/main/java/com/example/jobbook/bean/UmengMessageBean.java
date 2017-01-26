package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2017/1/26.
 */

public class UmengMessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int type;

    private String account;

    private String event;

    private String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
