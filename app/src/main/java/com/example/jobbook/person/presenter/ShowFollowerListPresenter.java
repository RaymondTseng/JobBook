package com.example.jobbook.person.presenter;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowFollowerListPresenter {

    void loadFollwers(String account);

    void follow(String myAccount, String hisAccount);
}
