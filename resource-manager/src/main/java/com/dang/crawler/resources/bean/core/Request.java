package com.dang.crawler.resources.bean.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mi on 2017/5/3.
 */
public class Request {
    private String url = "";
    private Map<String, String> header = new LinkedHashMap<String, String>();
    private String body = "";

    public Request(String url) {
        this.url = url;
    }

    /////////////////////////////////
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
