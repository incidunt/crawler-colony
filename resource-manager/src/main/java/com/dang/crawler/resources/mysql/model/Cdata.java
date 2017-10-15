package com.dang.crawler.resources.mysql.model;

import java.util.Date;

/**
 * Created by dang on 17-6-7.
 */
public class Cdata {
    private int id;
    private String jobId;
    private String ckey;
    private Date date;
    private String cdata;
    public Cdata(){}
    public Cdata(String jobId,String key,String data){
        this.jobId = jobId;
        this.ckey= key;
        this.cdata = data;
    }
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCdata() {
        return cdata;
    }

    public void setCdata(String cdata) {
        this.cdata = cdata;
    }
}
