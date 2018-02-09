package com.dang.net.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * HttpClient工具类
 */
public class HttpClientFactory {


    public static CloseableHttpClient createDefault(){
        return HttpClients.createDefault();
    }

    public static CloseableHttpClient create(){
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRetryHandler(new HttpRequestRetryHandlerImpl())     // 自定义是否重试
                .setRedirectStrategy( new LaxRedirectStrategy())        // 支持重定向
                //  .setRoutePlanner(routePlanner)                      // 设置代理
                .build();
        return httpclient;
    }

    static class HttpRequestRetryHandlerImpl implements HttpRequestRetryHandler {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 5) {  // 最大重试次数 5
                return false;
            }
            if (exception instanceof InterruptedIOException) {  // 超时重试
                return true;
            }
            if (exception instanceof UnknownHostException) {    //  Unknown host 不重试
                return false;
            }
            if (exception instanceof ConnectTimeoutException) { //  连接拒绝 不重试 Connection refused
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    }
}