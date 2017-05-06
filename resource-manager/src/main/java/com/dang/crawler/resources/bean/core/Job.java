package com.dang.crawler.resources.bean.core;

import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class Job {
    private List<CrawlerMQ> crawlerMQList;
    private String jobName;
    public Job(List<CrawlerMQ> crawlerMQList , String jobName){
        this.crawlerMQList = crawlerMQList;
        this.jobName = jobName;
    }
    public Job(List<CrawlerMQ> crawlerMQList ,CrawlerJob crawlerJob){
        this.crawlerMQList = crawlerMQList;
        if(crawlerMQList!=null&&crawlerMQList.size()>0){
            try {
                crawlerJob.work(crawlerMQList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
