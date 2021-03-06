package com.example.jobbook.userdetail.presenter;

/**
 * Created by root on 16-12-5.
 */

public interface UserDetailPresenter {

    void follow(String myAccount, String hisAccount);

    void loadUserDetailByAccount(String account);

    void unfollow(String myAccount, String hisAccount);

    void refreshPersonBean();

}
