package com.dang.crawler.resources.mysql.dao;


import com.dang.crawler.resources.mysql.model.CrawlerJob;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface CrawlerJobMapper {
    void insert(CrawlerJob crawlerJob);
    void delete(String jobId);
    void update(CrawlerJob crawlerJob);
    CrawlerJob select(CrawlerJob crawlerJob);
    List<CrawlerJob> list(CrawlerJob crawlerJob);
}
