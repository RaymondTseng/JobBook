package com.example.jobbook.search.model;

/**
 * Created by Xu on 2016/8/31.
 */
public interface SearchModel {

    void search(int type, String content, int pageIndex, SearchModelImpl.OnSearchListener listener);
}
