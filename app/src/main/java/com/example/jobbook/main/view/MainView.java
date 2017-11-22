package com.example.jobbook.main.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.PersonBean;

/**
 * Created by Xu on 2016/7/5.
 */
public interface MainView extends IBaseView{

    void switch2Job();

    void switch2Article();

    void switch2Question();

    void switch2Container();

    void loginCheckSuccess(PersonBean personBean);

    void loginCheckTimeOut();

}
