package com.dang.crawler.resources.mysql.model;

/**
 * Created by dangqihe on 2017/4/25.
 */
public class Project {
    private int id;
    private String name;
    private String note;

    //////////////////////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
