package com.example.jobbook.userdetail.model;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailMomentModel {

    void loadUserDetailMoments(String account,
                               UserDetailMomentModelImpl.OnLoadUserDetailMomentListener listener);
}
