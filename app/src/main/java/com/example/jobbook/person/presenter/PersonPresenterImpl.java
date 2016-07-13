package com.example.jobbook.person.presenter;

import com.example.jobbook.person.model.PersonModel;
import com.example.jobbook.person.model.PersonModelImpl;
import com.example.jobbook.person.view.PersonView;

/**
 * Created by Xu on 2016/7/6.
 */
public class PersonPresenterImpl implements PersonPresenter{

    private PersonView mPersonView;
    private PersonModel mPersonModel;

    public PersonPresenterImpl(PersonView view) {
        mPersonView = view;
        mPersonModel = new PersonModelImpl();
    }

    @Override
    public void loadPersonData() {

    }

    public interface OnLoadPersonDataListener {
        void onSuccess();
        void onFailure();
    }
}
