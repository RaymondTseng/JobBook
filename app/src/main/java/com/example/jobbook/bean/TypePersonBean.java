package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by root on 17-1-19.
 */

public class TypePersonBean extends PersonBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
