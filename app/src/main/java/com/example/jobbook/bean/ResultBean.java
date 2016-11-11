package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/8/25.
 * 返回结果类
 */
public class ResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
