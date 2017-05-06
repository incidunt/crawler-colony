package com.dang.crawler.core.fetcher.http;

import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.fetcher.bean.PostInfo;
import com.dang.crawler.core.fetcher.bean.ProxyInfo;
import com.dang.crawler.core.fetcher.bean.RequestInfo;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class Fetcher {
    private static final Log LOG = LogFactory.getLog(Fetcher.class);
    private PoolingHttpClientConnectionManager cm = null;
    private RequestInfo requestInfo = new RequestInfo();

    protected Fetcher() {
        this.cm = this.buildConnectionManager();
    }

    public static Fetcher bootstrap() {
        return new Fetcher();
    }

    public Fetcher buildRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
        return this;
    }

    private CloseableHttpClient initHttpClient(ProxyInfo proxyInfo) {
        return HttpClients.custom().setRetryHandler(this.addRetryHandler()).setUserAgent(this.requestInfo.getUserAgent()).setDefaultCredentialsProvider(this.buildCredentialsProvider(proxyInfo)).setDefaultAuthSchemeRegistry(this.buildRegistry()).setConnectionManager(this.cm).setDefaultRequestConfig(this.buildRequestConfig(proxyInfo)).build();
    }

    private ConnectionConfig buildConnectionConfig() {
        return ConnectionConfig.custom().setCharset(Consts.UTF_8).setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).build();
    }

    private RequestConfig buildRequestConfig(ProxyInfo proxyInfo) {
        return RequestConfig.custom().setSocketTimeout(this.requestInfo.getSocketTimeout()).setConnectionRequestTimeout(this.requestInfo.getConnRequestTimeout()).setConnectTimeout(this.requestInfo.getConnTimeout()).setProxy(this.buildProxy(proxyInfo)).setContentCompressionEnabled(this.requestInfo.isContentCompressionEnabled()).setCookieSpec("standard-strict").setCircularRedirectsAllowed(false).build();
    }

    private HttpHost buildProxy(ProxyInfo proxyInfo) {
        return proxyInfo == null?null:new HttpHost(proxyInfo.getHost(), proxyInfo.getPort().intValue());
    }

    private CredentialsProvider buildCredentialsProvider(ProxyInfo proxyInfo) {
        if(proxyInfo == null) {
            return null;
        } else {
            BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(proxyInfo.getHost(), proxyInfo.getPort().intValue()), new UsernamePasswordCredentials(proxyInfo.getUsername(), proxyInfo.getPassword()));
            return credsProvider;
        }
    }

    private ProxyInfo generateProxy(ProxyInfo proxyInfo) {
        return proxyInfo != null?proxyInfo:null;
    }

    private Registry<AuthSchemeProvider> buildRegistry() {
        Registry authSchemeRegistry = RegistryBuilder.create().register("NTLM", new JCIFSNTLMSchemeFactory()).register("Basic", new BasicSchemeFactory()).register("Digest", new DigestSchemeFactory()).register("Negotiate", new SPNegoSchemeFactory()).register("Kerberos", new KerberosSchemeFactory()).build();
        return authSchemeRegistry;
    }

    private HttpRequestRetryHandler addRetryHandler() {
        return !this.requestInfo.isRetryable()?null:new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                Fetcher.LOG.error(exception.getMessage(), exception);

                try {
                    Thread.sleep((long)Fetcher.this.requestInfo.getRetryInterval());
                    Fetcher.LOG.info("It begins to retry the request. The retry number is  " + executionCount);
                } catch (Exception var5) {
                    Fetcher.LOG.error(var5.getMessage(), var5);
                }

                return executionCount > Fetcher.this.requestInfo.getRetryCnt()?false:exception instanceof IOException;
            }
        };
    }

    private PoolingHttpClientConnectionManager buildConnectionManager() {
        PlainConnectionSocketFactory plainsf = PlainConnectionSocketFactory.INSTANCE;
        SSLConnectionSocketFactory sslsf = null;

        try {
            sslsf = this.buildTrustSSLSocketFactory();
        } catch (Exception var6) {
            LOG.error(var6.getMessage(), var6);
        }

        Registry r = RegistryBuilder.create().register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        cm.setMaxTotal(this.requestInfo.getConnPoolMaxTotal());
        cm.setDefaultMaxPerRoute(this.requestInfo.getConnPoolMaxPerRoute());
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        cm.setDefaultConnectionConfig(this.buildConnectionConfig());
        cm.setDefaultSocketConfig(socketConfig);
        HttpClients.custom().setConnectionManager(cm).build();
        return cm;
    }

    public Page requestGet(String url, ProxyInfo proxyInfo, Map<String, String> requestHeaders) throws IOException {
        CloseableHttpClient httpClient = this.initHttpClient(proxyInfo);
        return this.fetchGet(url, httpClient, requestHeaders);
    }

    public Page requestPost(String url, ProxyInfo proxyInfo, Map<String, String> requestHeaders, PostInfo postInfo) throws IOException {
        CloseableHttpClient httpClient = this.initHttpClient(proxyInfo);
        return this.fetchPost(url, httpClient, requestHeaders, postInfo, proxyInfo);
    }

    private Page fetchPost(String url, CloseableHttpClient httpClient, Map<String, String> requestHeaders, PostInfo postInfo, ProxyInfo proxyInfo) throws IOException {
        Page page = new Page();

        try {
            HttpPost httpPost = this.generateHttpPost(url, postInfo);
            LOG.info("Post request -> " + url);
            this.setRequestHeaders(httpPost, requestHeaders);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if(this.requestInfo.isPostRedirectEnabled() && (response.getStatusLine().getStatusCode() == 301 || response.getStatusLine().getStatusCode() == 302)) {
                String location = response.getFirstHeader("Location").getValue();
                if(StringUtils.isNotBlank(location)) {
                    if(!location.matches("http[s]?://.*[/]?.*")) {
                        Matcher headers = Pattern.compile("http[s]?://.*/").matcher(url);
                        if(headers.find()) {
                            location = headers.group() + location;
                        }
                    }

                    Header[] var20 = response.getHeaders("Set-Cookie");
                    StringBuilder cookie = new StringBuilder();
                    Header[] arr$ = var20;
                    int len$ = var20.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        Header header = arr$[i$];
                        String[] cookieStrings = header.getValue().split(";");
                        cookie.append(cookieStrings[0]).append("; ");
                    }

                    requestHeaders.put("Cookie", cookie.toString());
                    Page var21 = bootstrap().requestGet(location, proxyInfo, requestHeaders);
                    return var21;
                }
            }

            this.generatePage(page, response);
        } finally {
            this.close();
        }

        return page;
    }

    private <T> void setRequestHeaders(T request, Map<String, String> requestHeaders) {
        if(requestHeaders != null && requestHeaders.size() > 0) {
            Iterator i$ = requestHeaders.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                if(request instanceof HttpGet) {
                    ((HttpGet)request).setHeader((String)entry.getKey(), (String)entry.getValue());
                } else if(request instanceof HttpPost) {
                    ((HttpPost)request).setHeader((String)entry.getKey(), (String)entry.getValue());
                }
            }
        }

    }

    private void generatePage(Page page, CloseableHttpResponse response) throws IOException {
        try {
            HttpEntity entity = response.getEntity();
            byte[] responseBytes = EntityUtils.toByteArray(entity);
            page.setResponseBytes(responseBytes);
            page.setContent(new String(responseBytes, this.getEncode(responseBytes)));
            page.setStatusCode(Integer.valueOf(response.getStatusLine().getStatusCode()));
            page.setResponse(response);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

    }

    private HttpPost generateHttpPost(String url, PostInfo postInfo) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if(postInfo == null) {
            LOG.error("Post type and body is empty error.");
            return null;
        } else {
            String charset = this.requestInfo.getPostBodyEncodeCharset();
            PostTypeEnum postTypeEnum = postInfo.getPostEnum();
            if(PostTypeEnum.NAME_VALUE == postTypeEnum) {
                ArrayList myEntity = new ArrayList();
                Iterator nvps = postInfo.getPostParamsMap().entrySet().iterator();

                while(nvps.hasNext()) {
                    Map.Entry arr$ = (Map.Entry)nvps.next();
                    myEntity.add(new BasicNameValuePair((String)arr$.getKey(), (String)arr$.getValue()));
                }

                httpPost.setEntity(new UrlEncodedFormEntity(myEntity, Charset.forName(charset)));
            } else if(PostTypeEnum.STRING == postTypeEnum && postInfo.getPostContentString() != null) {
                String[] var14 = postInfo.getPostContentString().split("&");
                ArrayList var15 = new ArrayList();
                String[] var16 = var14;
                int len$ = var14.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String entry = var16[i$];
                    String[] postKeyValue = entry.split("=");
                    if(postKeyValue.length == 2) {
                        var15.add(new BasicNameValuePair(postKeyValue[0].trim(), postKeyValue[1].trim()));
                    }
                }

                httpPost.setEntity(new UrlEncodedFormEntity(var15, Charset.forName(charset)));
            } else {
                StringEntity var13 = new StringEntity(postInfo.getPostContentString(), Charset.forName(charset));
                if(PostTypeEnum.XML == postTypeEnum) {
                    httpPost.addHeader("Content-Type", "text/xml; charset=" + charset);
                    httpPost.setEntity(var13);
                } else if(PostTypeEnum.JSON == postTypeEnum) {
                    httpPost.addHeader("Content-Type", "application/json; charset=" + charset);
                    httpPost.setEntity(var13);
                }
            }

            return httpPost;
        }
    }

    private String getEncode(byte[] data) {
        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        CharsetMatch match = detector.detect();
        String encoding = match.getName();
        CharsetMatch[] matches = detector.detectAll();
        if(LOG.isDebugEnabled()) {
            CharsetMatch[] arr$ = matches;
            int len$ = matches.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                CharsetMatch m = arr$[i$];
                LOG.debug("CharsetName:" + m.getName() + " Confidence:" + m.getConfidence());
            }
        }

        return encoding;
    }

    private Page fetchGet(String url, CloseableHttpClient httpClient, Map<String, String> requestHeaders) throws IOException {
        Page page = new Page();

        try {
            HttpGet httpGet = new HttpGet(url);
            LOG.info("Get request -> " + url);
            this.setRequestHeaders(httpGet, requestHeaders);
            this.generatePage(page, httpClient.execute(httpGet));
        } finally {
            this.close();
        }

        return page;
    }

    private void close() {
        this.cm.closeIdleConnections((long)this.requestInfo.getConnidleTime(), TimeUnit.SECONDS);
    }

    private SSLConnectionSocketFactory buildTrustSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init((KeyManager[])null, new TrustManager[]{xtm}, (SecureRandom)null);
        Fetcher.NoopHostnameSSLSocketFactory socketFactory = new Fetcher.NoopHostnameSSLSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
        return socketFactory;
    }

    private class NoopHostnameSSLSocketFactory extends SSLConnectionSocketFactory {
        private static final String SSLV3 = "SSLv3";
        private static final String TLSV1 = "TLSv1";

        public NoopHostnameSSLSocketFactory(SSLContext sslContext, NoopHostnameVerifier hostnameVerifier) {
            super(sslContext, hostnameVerifier);
        }

        protected void prepareSocket(SSLSocket socket) throws IOException {
            socket.setEnabledProtocols(new String[]{"SSLv3", "TLSv1"});
        }
    }
}
