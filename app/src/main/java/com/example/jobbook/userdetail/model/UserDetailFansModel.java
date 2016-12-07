package com.example.jobbook.userdetail.model;

/**
 * Created by root on 16-11-30.
 */

public interface UserDetailFansModel  {

    void loadFans(String account, UserDetailFansModelImpl.OnLoadFansListener listener);
}
