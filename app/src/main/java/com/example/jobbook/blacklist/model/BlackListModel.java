package com.example.jobbook.blacklist.model;

/**
 * Created by Xu on 2016/12/8.
 */

public interface BlackListModel {

    void loadBlackList(BlackListModelImpl.OnLoadBlackListListener listener);
}
