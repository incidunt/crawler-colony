package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.Table;

/**
 * Created by dang on 17-6-9.
 */
public interface TableMapper {
    void tryCreate(Table table);
    void insert(Table table);
}
