package com.dang.crawler.core.fetcher.service;

import com.dang.crawler.core.fetcher.bean.*;
import com.dang.crawler.core.fetcher.http.Fetcher;
import com.dang.crawler.core.fetcher.http.PostTypeEnum;
import com.dang.crawler.resources.bean.core.CrawlerMQ;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;

/**
 * Created by duang on 2017/4/26.
 */
public class PageService {
    private Fetcher fetcher = getFetcher();
    public static Page fetcher(CrawlerMQ crawlerMQ, ProxyInfo proxyInfo) throws IOException {
        Fetcher fetcher = getFetcher();
        Page fetcherPage;
        if(StringUtils.isEmpty(crawlerMQ.getRequest().getBody())){//没有body 是get请求
            fetcherPage = fetcher.requestGet(crawlerMQ.getRequest().getUrl(), proxyInfo,crawlerMQ.getRequest().getHeader());
        }else{//有请求body体  使用post
            PostInfo postInfo = new PostInfo();
            postInfo.setPostEnum(PostTypeEnum.STRING);
            postInfo.setPostContentString(crawlerMQ.getRequest().getBody());
            fetcherPage = fetcher.requestPost(crawlerMQ.getRequest().getUrl(),proxyInfo,crawlerMQ.getRequest().getHeader(),postInfo);
        }
        return fetcherPage;
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
