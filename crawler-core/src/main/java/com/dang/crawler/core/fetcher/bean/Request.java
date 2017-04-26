package com.dang.crawler.core.fetcher.bean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by duang on 2017/4/26.
 */
public class Request {
    private String url = "";
    private Map<String,String> header = new LinkedHashMap<String,String>();
    private String body = "";
    public Request(String url){
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
    public void setHeader(Map<String,String> header) {
        this.header = header;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
