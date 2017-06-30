package com.example.jobbook.api.bean;

import com.example.jobbook.bean.ArticleBean;

import java.util.List;

/**
 * Created by Xu on 2017/6/29.
 */

public class ArticleListWrapper {

    private String status;
    private List<ArticleBean> response;

    public List<ArticleBean> getResponse() {
        return response;
    }

    public void setResponse(List<ArticleBean> response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
