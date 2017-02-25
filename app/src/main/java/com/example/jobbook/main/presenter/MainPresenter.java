package com.example.jobbook.main.presenter;

import android.content.Context;

/**
 * Created by Xu on 2016/7/5.
 */
public interface MainPresenter {

    void switchNavigation(int id);

//    PersonBean loadPersonBean(Context context);
//
//    void savePersonBean(Context context, PersonBean personBean);

    void loginCheck(Context context);

}
