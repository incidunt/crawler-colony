package com.dang.crawler.resources.mysql.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-6-15.
 */
public interface SqlMapper {
    void insert(String sql);
    void delete(String sql);
    void updata(String sql);
    List<Map<String,Object>> select(String sql);
}
