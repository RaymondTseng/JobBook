package com.example.jobbook.square.presenter;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquarePresenter {

    void loadSquare(int pageIndex, String name);

    void like(int squareId, int position);

    void unlike(int squareId, int position);

}
