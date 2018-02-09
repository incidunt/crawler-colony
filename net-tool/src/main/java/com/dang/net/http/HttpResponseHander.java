package com.dang.net.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * Description:
 *
 * @Date Create in 2018/2/7
 */
public class HttpResponseHander {
    private HttpResponse httpResponse;

    public HttpResponseHander(HttpResponse httpResponse){
        this.httpResponse = httpResponse;
    }


    public String content() throws IOException {
        return EntityUtils.toString(httpResponse.getEntity());
    }
}
