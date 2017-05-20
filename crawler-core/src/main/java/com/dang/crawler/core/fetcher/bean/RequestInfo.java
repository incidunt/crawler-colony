package com.dang.crawler.core.fetcher.bean;

import com.dang.crawler.core.fetcher.http.HttpParamsCons;
import java.io.Serializable;

public class RequestInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36";
    private int socketTimeout = 20000;
    private int connTimeout = 20000;
    private int connRequestTimeout = 20000;
    private boolean isRetryable = false;
    private int retryInterval = 3000;
    private int connidleTime = 30;
    private int connPoolMaxTotal = 5000;
    private int connPoolMaxPerRoute = 1000;
    private int retryCnt = 2;
    private Long connManagerTimeout;
    private String postBodyEncodeCharset;
    private boolean contentCompressionEnabled;
    private boolean postRedirectEnabled;

    public RequestInfo() {
        this.connManagerTimeout = HttpParamsCons.CONN_MANAGER_TIMEOUT;
        this.postBodyEncodeCharset = "UTF-8";
        this.contentCompressionEnabled = true;
        this.postRedirectEnabled = false;
    }

    public Long getConnManagerTimeout() {
        return this.connManagerTimeout;
    }

    public void setConnManagerTimeout(Long connManagerTimeout) {
        this.connManagerTimeout = connManagerTimeout;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public int getConnRequestTimeout() {
        return this.connRequestTimeout;
    }

    public void setConnRequestTimeout(int connRequestTimeout) {
        this.connRequestTimeout = connRequestTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnTimeout() {
        return this.connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public boolean isRetryable() {
        return this.isRetryable;
    }

    public void setRetryable(boolean isRetryable) {
        this.isRetryable = isRetryable;
    }

    public int getRetryCnt() {
        return this.retryCnt;
    }

    public void setRetryCnt(int retryCnt) {
        this.retryCnt = retryCnt;
    }

    public int getRetryInterval() {
        return this.retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getConnidleTime() {
        return this.connidleTime;
    }

    public void setConnidleTime(int connidleTime) {
        this.connidleTime = connidleTime;
    }

    public int getConnPoolMaxTotal() {
        return this.connPoolMaxTotal;
    }

    public void setConnPoolMaxTotal(int connPoolMaxTotal) {
        this.connPoolMaxTotal = connPoolMaxTotal;
    }

    public int getConnPoolMaxPerRoute() {
        return this.connPoolMaxPerRoute;
    }

    public void setConnPoolMaxPerRoute(int connPoolMaxPerRoute) {
        this.connPoolMaxPerRoute = connPoolMaxPerRoute;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public String getPostBodyEncodeCharset() {
        return this.postBodyEncodeCharset;
    }

    public void setPostBodyEncodeCharset(String postBodyEncodeCharset) {
        this.postBodyEncodeCharset = postBodyEncodeCharset;
    }

    public boolean isContentCompressionEnabled() {
        return this.contentCompressionEnabled;
    }

    public void setContentCompressionEnabled(boolean contentCompressionEnabled) {
        this.contentCompressionEnabled = contentCompressionEnabled;
    }

    public boolean isPostRedirectEnabled() {
        return this.postRedirectEnabled;
    }

    public void setPostRedirectEnabled(boolean postRedirectEnabled) {
        this.postRedirectEnabled = postRedirectEnabled;
    }
}
