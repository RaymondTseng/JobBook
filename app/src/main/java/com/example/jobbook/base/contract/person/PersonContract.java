package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2018/2/14.
 */

public interface PersonContract {

    interface View extends IBaseView {
        boolean showPersonData();
    }
}
