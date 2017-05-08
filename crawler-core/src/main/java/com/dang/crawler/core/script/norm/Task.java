package com.dang.crawler.core.script.norm;

import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.resources.bean.core.CrawlerMQ;

import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class Task {
    private List<CrawlerMQ> crawlerMQList;
    private String jobName;
    public Task(List<CrawlerMQ> crawlerMQList , String jobName){
        this.crawlerMQList = crawlerMQList;
        this.jobName = jobName;
    }
    public Task(List<CrawlerMQ> crawlerMQList , Script script){
        this.crawlerMQList = crawlerMQList;
        if(crawlerMQList!=null&&crawlerMQList.size()>0){
            try {
                script.work(crawlerMQList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
