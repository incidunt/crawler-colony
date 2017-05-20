package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.Keyword;
import java.util.List;


/**
 * Created by dang on 17-5-16.
 */
public interface KeywordMapper {
    void insert(Keyword keyword);
    void insertAll(List<Keyword> keywords);
    List<Keyword> select(Keyword keyword);
    int maxKid(int projectId);
}
