package com.dang.crawler.core.fetcher.service;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.*;
import com.dang.crawler.core.fetcher.http.Fetcher;
import com.dang.crawler.core.fetcher.http.PostTypeEnum;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by duang on 2017/4/26.
 */
public class PageService {
    //private Fetcher fetcher = getFetcher();
    public static Page fetcher(Crawler crawler, ProxyInfo proxyInfo) throws IOException {
        //针对https采用SSL的方式创建httpclient
        if(crawler.getUrl().startsWith("https")){
            System.setProperty ("jsse.enableSNIExtension", "false");
        }
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type"," application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Wicket-Ajax","true");
        header.put("Accept","application/xml, text/xml, */*; q=0.01");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        header.put("Connection","keep-alive");
        header.put("X-Requested-With","XMLHttpRequest");
        if(crawler.getHeader()!=null && header.size()>0) {
            header.putAll(crawler.getHeader());
        }
        Fetcher fetcher = getFetcher();
        Page fetcherPage;
        if(crawler.getBody()==null||crawler.getBody().length()==0){//没有body 是get请求
            fetcherPage = fetcher.requestGet(crawler.getUrl(), proxyInfo,header);
        }else{//有请求body体  使用post
            PostInfo postInfo = new PostInfo();
            postInfo.setPostEnum(PostTypeEnum.STRING);
            postInfo.setPostContentString(crawler.getBody());
            fetcherPage = fetcher.requestPost(crawler.getUrl(),proxyInfo,header,postInfo);
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
