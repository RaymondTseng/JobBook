package com.example.jobbook.person.model;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowMomentListModel {

    void loadMomentList(String hisAccount, String myAccount, final ShowMomentListModelImpl.OnLoadMomentListListener listener);
}
