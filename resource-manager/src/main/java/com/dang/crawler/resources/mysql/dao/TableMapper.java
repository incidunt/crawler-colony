package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-6-9.
 */
public interface TableMapper {

    void tryCreate(Table table);
    void insert(Table table);

    List<Map<String,Object>> select(@Param("table")String table, @Param("limit")int limit, @Param("size") int size);

}
