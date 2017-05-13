package com.dang.crawler.core.control.bean;

/**
 * Created by dang on 17-5-10.
 */
public class Job {
    public String jobId;
    private int priority;
    private int maxThread;
    private long flag;
    private int CrawlerCounter;
    private int workThread;
    //////////////////////////////////////
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public int getCrawlerCounter() {
        return CrawlerCounter;
    }

    public void setCrawlerCounter(int crawlerCounter) {
        CrawlerCounter = crawlerCounter;
    }

    public int getWorkThread() {
        return workThread;
    }

    public void setWorkThread(int workThread) {
        this.workThread = workThread;
    }
}
