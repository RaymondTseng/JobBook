package com.example.jobbook.userdetail.model;

/**
 * Created by root on 16-11-30.
 */

public interface UserDetailFollowModel {

    void loadFollow(String account, UserDetailFollowModelImpl.OnLoadFollowListener listener);

    void follow(String myAccount, String hisAccount, UserDetailFollowModelImpl.OnFollowListener listener);
}
