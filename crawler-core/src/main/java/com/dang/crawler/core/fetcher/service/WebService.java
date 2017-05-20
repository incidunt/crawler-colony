package com.dang.crawler.core.fetcher.service;

import com.dang.crawler.core.fetcher.bean.ProxyInfo;
import com.dang.crawler.resources.utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by duang on 2017/4/27.
 */
public class WebService {
    private  static  Logger logger = LoggerFactory.getLogger(WebService.class);
    public static WebDriver phantomjs(ProxyInfo proxyInfo){
        //logger.info("get:"+request.getUrl());
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                PropertiesUtils.getProperty("selenium.phantomjs.driver.path"));
        // set user-agent
        caps.setCapability("phantomjs.page.settings.userAgent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:25.0) Gecko/20100101 Firefox/25.0 ");
        // set request headers
        caps.setCapability("phantomjs.page.customHeaders.user-id", "dang");
        caps.setCapability("phantomjs.page.settings.resourceTimeout", "10000");
        caps.setCapability("phantomjs.page.settings.loadImages", "false");
        caps.setBrowserName("Firefox");
        ConcurrentLinkedDeque<String> proxyQueue=new ConcurrentLinkedDeque<>();
        String proxyLine=proxyQueue.poll();
        // set proxy
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        if(proxyInfo!=null) {
            cliArgsCap.add("--proxy=" + proxyInfo.getHost() + ":" + proxyInfo.getPort());
            cliArgsCap.add("--proxy-auth=" + proxyInfo.getUsername() + ":" + proxyInfo.getPassword());
            cliArgsCap.add("--proxy-type=http");
        }
        // cliArgsCap.add("--proxy-type=socks5");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        //WebDriver webDriver = new ChromeDriver(caps);
        WebDriver webDriver = new PhantomJSDriver(caps);
        return webDriver;
    }

}
