package com.example.jobbook.api.bean;

/**
 * Created by Xu on 2016/8/25.
 * 返回结果类
 */
public class ResultBean<T> {

    private String status;

    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
