package com.dang.crawler.core.fetcher;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class APIHttpClient {

    // 接口地址
    private static String apiURL = "https://www.darenloan.com/rf/support/validate/phoneAccount";
    private Log logger = LogFactory.getLog(this.getClass());
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
    private HttpClient httpClient = null;
    private HttpPost method = null;
    private long startTime = 0L;
    private long endTime = 0L;
    private int status = 0;

    /**
     * 接口地址
     *
     * @param url
     */
    public APIHttpClient(String url) {

        if (url != null) {
            this.apiURL = url;
        }
        if (apiURL != null) {
            httpClient = new DefaultHttpClient();
            method = new HttpPost(apiURL);

        }
    }

    /**
     * 调用 API
     *
     * @param parameters
     * @return
     */
    public String post(String parameters) {
        String body = null;
        logger.info("parameters:" + parameters);

        if (method != null & parameters != null
                && !"".equals(parameters.trim())) {
            try {

                // 建立一个NameValuePair数组，用于存储欲传送的参数
                method.addHeader("AJAX","ajax");
                method.addHeader("Accept","application/json, text/plain, */*");
                method.addHeader("Accept-Encoding", "gzip, deflate, br");
                method.addHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
                method.addHeader("Cache-Control","no-cache");
                method.addHeader("Connection","keep-alive");
                method.addHeader("Content-Type","application/json;charset=utf-8");
                method.addHeader("Host","www.darenloan.com");
                method.addHeader("If-Modified-Since","0");
                method.addHeader("Referer","https://www.darenloan.com/passport/register");
                method.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0");
                method.addHeader("Cookie",
                        "__jsluid=080a2e6c11638ca10cb71bff3cfff561; UM_distinctid=15c626f0e551c-0539721b8d05378-173e7240-1fa400-15c626f0e5630c; CNZZDATA1256195134=1212708605-1496298540-%7C1496310009; CNZZDATA1256297050=119119100-1496298216-%7C1496307342; _umdata=55F3A8BFC9C50DDA2DC798BF8165FAF0506290575A61BD930FAC28153C0BDE0FF0672C1BC5662C33CD43AD3E795C914CFE85ABB27C1C2CB6D3739FFF02187EDD ; JSESSIONID=BD70C876161910A9C20989CEBE7E64A7");
                method.addHeader("","");
                method.setEntity(new StringEntity(parameters, Charset.forName("utf-8")));
                startTime = System.currentTimeMillis();

                HttpResponse response = httpClient.execute(method);

                endTime = System.currentTimeMillis();
                int statusCode = response.getStatusLine().getStatusCode();

                logger.info("statusCode:" + statusCode);
                logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("Method failed:" + response.getStatusLine());
                    status = 1;
                }

                // Read the response body
                body = EntityUtils.toString(response.getEntity());

            } catch (IOException e) {
                // 网络错误
                status = 3;
            } finally {
                logger.info("调用接口状态：" + status);
            }

        }
        return body;
    }

    public static void main(String[] args) {
        APIHttpClient ac = new APIHttpClient(apiURL);
        JsonArray arry = new JsonArray();
        JsonObject j = new JsonObject();
        j.addProperty("validate", "17060910209");
        arry.add(j);
        System.out.println(ac.post(j.toString()));
    }

    /**
     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }
}