package com.dang.crawler.core.fetcher.service;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.*;
import com.dang.crawler.core.fetcher.http.Fetcher;
import com.dang.crawler.core.fetcher.http.PostTypeEnum;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;

/**
 * Created by duang on 2017/4/26.
 */
public class PageService {
    private Fetcher fetcher = getFetcher();
    public static Page fetcher(Crawler crawler, ProxyInfo proxyInfo) throws IOException {
        Fetcher fetcher = getFetcher();
        Page fetcherPage;
        if(StringUtils.isEmpty(crawler.getBody())){//没有body 是get请求
            fetcherPage = fetcher.requestGet(crawler.getUrl(), proxyInfo,crawler.getHeader());
        }else{//有请求body体  使用post
            PostInfo postInfo = new PostInfo();
            postInfo.setPostEnum(PostTypeEnum.STRING);
            postInfo.setPostContentString(crawler.getBody());
            fetcherPage = fetcher.requestPost(crawler.getUrl(),proxyInfo,crawler.getHeader(),postInfo);
        }
        return fetcherPage;
    }
    public static Page fetcher(Crawler crawler) throws IOException {
        return fetcher(crawler,null);
    }
    public static Fetcher getFetcher(){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRetryable(true);
        requestInfo.setPostRedirectEnabled(true);
        requestInfo.setRetryCnt(2);
        requestInfo.setConnTimeout(30000);
        requestInfo.setConnRequestTimeout(30000);
        requestInfo.setSocketTimeout(60000);
        Fetcher fetcher = Fetcher.bootstrap().buildRequestInfo(requestInfo);
        return fetcher;
    }
}
