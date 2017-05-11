package com.dang.crawler.core.script.norm;

import com.dang.crawler.core.control.bean.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface  Script extends Serializable {
    Logger log = LoggerFactory.getLogger(Script.class);
    List<Task> work(Crawler  crawler) throws Exception;
}
