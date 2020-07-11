package com.sanim.articular.Interface;

import com.sanim.articular.Model.Article;

import java.util.List;

public interface IArticulLoadDone {
    void onArticulLoadDoneListener(List<Article> articleList);
}
