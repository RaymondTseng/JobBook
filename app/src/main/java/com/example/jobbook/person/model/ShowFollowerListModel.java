package com.example.jobbook.person.model;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowFollowerListModel {

    void loadFollowerList(String account , final ShowFollowerListModelImpl.OnLoadFollowerListListener listener);

    void follow(String myAccount, String hisAccount, final ShowFollowerListModelImpl.OnFollowListener listener);
}
