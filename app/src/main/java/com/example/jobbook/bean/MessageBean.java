package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2017/1/21.
 */

public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int FOLLOW = 1;

    public static final int LIKE = 2;

    public static final int COMMENT = 3;

    private String id;

    private String time;

    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
