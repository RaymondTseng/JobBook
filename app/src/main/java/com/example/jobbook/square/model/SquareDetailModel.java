package com.example.jobbook.square.model;

/**
 * Created by root on 16-11-23.
 */

public interface SquareDetailModel {

    void loadSquareComments(int id, SquareDetailModelImpl.OnLoadSquareDetailCommentListener listener);
}
