package com.dang.crawler.core.control;

import com.dang.crawler.resources.bean.core.CrawlerJob;
import com.dang.crawler.resources.bean.core.Job;

import java.util.List;

/**
 * Created by duang on 2017/5/1.
 */
public interface Script {
    List<CrawlerJob> create() throws Exception;
    List<Job> init() throws Exception;
    void finish();
}
