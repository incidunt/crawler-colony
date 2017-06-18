package com.dang.crawler.resources.mysql.model;

import java.util.Date;

/**
 * Created by dang on 2017/5/8.
 */
public class CrawlerLog {
    private int id;
    private String jobId;
    private long flag;
    private String taskName;
    private long successCount;
    private long dbCount;
    private long failCount;
    private long toDoCount;
    private Date createDate;
    private Date modifiedDate;

    public CrawlerLog(){}
    public CrawlerLog(String jobId,String taskName,long flag){
        this.jobId = jobId;
        this.taskName = taskName;
        this.flag = flag;
    }
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

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
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
