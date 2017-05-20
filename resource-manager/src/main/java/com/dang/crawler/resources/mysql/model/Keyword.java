package com.dang.crawler.resources.mysql.model;

import java.util.Date;

/**
 * Created by dang on 2017/5/8.
 */
public class Keyword {
    private int projectId;
    private int kid ;
    private String keyword;
    private String note;
    private Date createDate;

    private int start;//设置分页开始
    private int limit =1000;//设置分页的每页的数量

    public Keyword(){}
    public Keyword(int projectId,int kid ,String keyword ,String note){
        this.projectId =projectId;
        this.kid = kid;
        this.keyword = keyword;
        this.note = note;
    }
    public int getId() {
        return kid;
    }

    public void setId(int id) {
        this.kid = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    @Override
    public String toString(){
        return keyword;
    }
}

