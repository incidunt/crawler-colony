package com.dang.net.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpHander {

    public static final CloseableHttpClient HTTP_CLIENT = HttpClientFactory.create();

    private String url;
    private List<BasicNameValuePair> paramList = new ArrayList<>();
    private Map<String, String> headerMap = new LinkedHashMap<>();

    public static enum TYPE{GET, POST}

    public static HttpHander hand(){
        return new HttpHander();
    }
    public HttpHander url(String url) {
        if(url.contains("http")) {
            this.url = url;
        }else {
            this.url = "http://" + url;
        }
        return this;
    }

    public HttpHander param(String key,String value){
        paramList.add(new BasicNameValuePair(key,value));
        return this;
    }

    public HttpHander header(String key, String value) {
        headerMap.put(key, value);
        return this;
    }

    public HttpResponseHander doGet() throws IOException {
        HttpGet get = new HttpGet(buildUrl(url));
        return execute(get);
    }
    private String buildUrl(String url){
        String urlStr = url;
        if(paramList != null && paramList.size() != 0){
            try {
                if(urlStr.endsWith("?")) {
                    urlStr = urlStr + "?" + EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
                }else {
                    urlStr = urlStr + EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
                }
            } catch (IOException e) {

            }
        }
        return urlStr;
    }

    public HttpResponseHander doPost() throws IOException {
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
        // 设置post求情参数
        httpPost.setEntity(entity);
        return execute(httpPost);
    }

    private HttpResponseHander execute(HttpUriRequest httpUriRequest) throws IOException {
        for(Map.Entry<String, String> entry : headerMap.entrySet()){
            httpUriRequest.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpUriRequest);
        return new HttpResponseHander(httpResponse);
    }

}
