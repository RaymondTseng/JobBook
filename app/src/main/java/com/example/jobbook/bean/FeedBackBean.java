package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/9/8.
 */
public class FeedBackBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String content;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
