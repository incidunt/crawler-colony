package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.Cdata;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface CdataMapper {
    void insert(Cdata cData);
    void insertAll(List<Cdata> cDataList);
    void delete(Cdata cData);
    void update(Cdata cData);
    List<Cdata> select(Cdata cData);
}
