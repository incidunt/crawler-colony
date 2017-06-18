package com.dang.crawler.resources.mysql.model;

/**
 * Created by mi on 2017/5/3.
 */
public class Source {
    private int id;
    private String name;
    private String url;
    private String note;

    /////////////////////////////////


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
