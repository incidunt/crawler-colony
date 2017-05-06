package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.CrawlerTask;

import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface CrawlerTaskMapper {
    CrawlerTask select(CrawlerTask crawlerTask);
    List<CrawlerTask> list();
    void insert(CrawlerTask crawlerTask);
    void update(CrawlerTask crawlerTask);
}
