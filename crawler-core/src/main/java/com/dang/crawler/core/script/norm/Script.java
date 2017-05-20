package com.dang.crawler.core.script.norm;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.List;

public interface Script extends Serializable,Cloneable {
    Logger log = LoggerFactory.getLogger(Script.class);
    //List<Task> results = new ArrayList<>();
    List<Task> work(final Crawler crawler,final Job job) throws Exception;
}
