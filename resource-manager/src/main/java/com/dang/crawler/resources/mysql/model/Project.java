package com.dang.crawler.resources.mysql.model;

/**
 * Created by dangqihe on 2017/4/25.
 */
public class Project {
    private int projectId;
    private String name;
    private String note;

    //////////////////////////////////////////////
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
