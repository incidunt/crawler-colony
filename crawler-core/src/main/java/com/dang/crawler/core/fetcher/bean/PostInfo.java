package com.dang.crawler.core.fetcher.bean;

import com.dang.crawler.core.fetcher.http.PostTypeEnum;
import java.io.Serializable;
import java.util.Map;

public class PostInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, String> postParamsMap;
    private String postContentString;
    private PostTypeEnum postEnum;

    public PostInfo() {
    }

    public PostInfo(Map<String, String> postParamsMap, String postContentString, PostTypeEnum postEnum) {
        this.postParamsMap = postParamsMap;
        this.postContentString = postContentString;
        this.postEnum = postEnum;
    }

    public PostInfo(String postContentString, PostTypeEnum postEnum) {
        this.postContentString = postContentString;
        this.postEnum = postEnum;
    }

    public PostInfo(Map<String, String> postParamsMap, PostTypeEnum postEnum) {
        this.postParamsMap = postParamsMap;
        this.postEnum = postEnum;
    }

    public PostTypeEnum getPostEnum() {
        return this.postEnum;
    }

    public void setPostEnum(PostTypeEnum postEnum) {
        this.postEnum = postEnum;
    }

    public Map<String, String> getPostParamsMap() {
        return this.postParamsMap;
    }

    public void setPostParamsMap(Map<String, String> postParamsMap) {
        this.postParamsMap = postParamsMap;
    }

    public String getPostContentString() {
        return this.postContentString;
    }

    public void setPostContentString(String postContentString) {
        this.postContentString = postContentString;
    }
}
