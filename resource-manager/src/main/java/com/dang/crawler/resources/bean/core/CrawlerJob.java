package com.dang.crawler.resources.bean.core;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface CrawlerJob extends Serializable {
    List<Job> work(CrawlerMQ crawlerMQ) throws Exception;
}
