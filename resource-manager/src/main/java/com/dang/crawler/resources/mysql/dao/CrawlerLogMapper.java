package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.CrawlerLog;

/**
 * Created by dang on 17-5-15.
 */
public interface CrawlerLogMapper {
    void insert(CrawlerLog crawlerlog);
    void update(CrawlerLog crawlerLog);
    Long getMaxFlag(String jobId);
}
