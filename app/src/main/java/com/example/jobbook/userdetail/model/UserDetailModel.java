package com.example.jobbook.userdetail.model;

/**
 * Created by root on 16-12-5.
 */

public interface UserDetailModel {

    void follow(String myAccount, String hisAccount, UserDetailModelImpl.OnFollowListener listener);

    void loadUserDetailByAccount(String account, UserDetailModelImpl.OnLoadUserDetailByAccountListener listener);
}
