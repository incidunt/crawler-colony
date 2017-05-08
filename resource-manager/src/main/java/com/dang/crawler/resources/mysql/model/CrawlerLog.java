package com.dang.crawler.resources.mysql.model;

import java.util.Date;

/**
 * Created by mi on 2017/5/8.
 */
public class CrawlerLog {
    private String jobId;
    private String taskName;
    private long flag;
    private long crawlerCount;
    private long dbCount;
    private long failCount;
    private long toDoCount;
    private Date createDate;
    private Date modifiedDate;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public long getCrawlerCount() {
        return crawlerCount;
    }

    public void setCrawlerCount(long crawlerCount) {
        this.crawlerCount = crawlerCount;
    }

    public long getFailCount() {
        return failCount;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    public long getToDoCount() {
        return toDoCount;
    }

    public void setToDoCount(long toDoCount) {
        this.toDoCount = toDoCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getDbCount() {
        return dbCount;
    }

    public void setDbCount(long dbCount) {
        this.dbCount = dbCount;
    }
}
