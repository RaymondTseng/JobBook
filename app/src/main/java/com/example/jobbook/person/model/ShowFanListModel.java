package com.example.jobbook.person.model;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowFanListModel {

    void loadFanList(String account, ShowFantListModelImpl.OnLoadFanListListener listener);
}
