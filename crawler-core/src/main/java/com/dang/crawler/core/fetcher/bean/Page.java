package com.dang.crawler.core.fetcher.bean;

import java.io.Serializable;
import org.apache.http.HttpResponse;

public class Page implements Serializable {
    private static final long serialVersionUID = -5146315260683733260L;
    private String content;
    private Integer statusCode;
    private HttpResponse response;
    private byte[] responseBytes;

    public Page() {
    }

    public HttpResponse getResponse() {
        return this.response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getResponseBytes() {
        return this.responseBytes;
    }

    public void setResponseBytes(byte[] responseBytes) {
        this.responseBytes = responseBytes;
    }

    public String getHeader(String key){
        return response.getFirstHeader(key).getValue();
    }
}
