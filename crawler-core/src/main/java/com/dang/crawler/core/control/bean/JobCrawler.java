package com.dang.crawler.core.control.bean;

/**
 * Created by dang on 17-5-11.
 */
public class JobCrawler {
    private Job job;
    private Crawler crawler;
    public JobCrawler(){}
    public JobCrawler(Job job,Crawler crawler){
        this.job = job;
        this.crawler = crawler;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Crawler getCrawler() {
        return crawler;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }
}
