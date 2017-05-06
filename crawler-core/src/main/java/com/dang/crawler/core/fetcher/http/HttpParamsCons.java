package com.dang.crawler.core.fetcher.http;

/**
 * Created by duang on 2017/4/26.
 */
public class HttpParamsCons {
    public static final int SOCKET_TIMEOUT = 20000;
    public static final int CONN_TIMEOUT = 20000;
    public static final int CONN_REQUEST_TIMEOUT = 20000;
    public static final boolean IS_RETRYABLE = false;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36";
    public static final String USER_NAME = "";
    public static final String PASSWORD = "";
    public static final String WORK_STATION = "";
    public static final String DOMAIN = "";
    public static final int CONN_POOL_IDLE_TIME = 30;
    public static final int CONN_POOL_MAX_TOTAL = 5000;
    public static final int CONN_POOL_PER_TOTAL = 1000;
    public static final int RETRY_INTERVAL = 3000;
    public static final int RETRY_TOTAL_CNT = 2;
    public static final Long CONN_MANAGER_TIMEOUT = Long.valueOf(500L);
    public static final String UTF_8 = "UTF-8";

    public HttpParamsCons() {
    }
}
