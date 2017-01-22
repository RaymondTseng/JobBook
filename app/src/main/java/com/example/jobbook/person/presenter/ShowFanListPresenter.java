package com.example.jobbook.person.presenter;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowFanListPresenter {

    void loadFans(String account);

    void follow(String myAccount, String hisAccount);
}
