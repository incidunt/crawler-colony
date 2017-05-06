package com.dang.crawler.resources.mysql.model;

/**
 * Created by dangqihe on 2017/4/25.
 */
public class Project {
    private int projectId;
    private String name;

    public int getId() {
        return projectId;
    }

    public void setId(int id) {
        this.projectId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
