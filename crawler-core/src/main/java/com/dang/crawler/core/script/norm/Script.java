package com.dang.crawler.core.script.norm;

import com.dang.crawler.resources.bean.core.CrawlerMQ;
import java.io.Serializable;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface  Script extends Serializable {
    List<Task> work(CrawlerMQ crawlerMQ) throws Exception;
}
