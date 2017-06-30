package com.example.jobbook.api.bean;

import com.example.jobbook.bean.ArticleBean;

/**
 * Created by Xu on 2017/6/30.
 */

public class ArticleWrapper {

    private String status;
    private ArticleBean response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArticleBean getResponse() {
        return response;
    }

    public void setResponse(ArticleBean response) {
        this.response = response;
    }
}
