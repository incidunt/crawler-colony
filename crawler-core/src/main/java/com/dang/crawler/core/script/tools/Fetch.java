package com.dang.crawler.core.script.tools;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.fetcher.service.PageService;
import com.dang.crawler.core.fetcher.service.WebDriverPool;
import com.dang.crawler.resources.utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

/**
 * Created by mi on 2017/5/9.
 */
public class Fetch {
    private static WebDriverPool webDriverPool = null;
    public static Page fetch(Crawler crawler) throws IOException {
        return PageService.fetcher(crawler,null);
    }
//    private static void webWork(WebWork webWork) throws Exception {
//        if(webDriverPool == null){
//            webDriverPool = new WebDriverPool(PropertiesUtils.getInt("webDriverPool.minPoolSize"),PropertiesUtils.getInt("webDriverPool.MaxPoolSize"));
//        }
//        WebDriver driver = webDriverPool.getFree();
//        webWork.work(driver);
//        webDriverPool.toFree(driver);
//    }
    public static synchronized WebDriver getWebDriver(){
        if(webDriverPool == null){
            webDriverPool = new WebDriverPool(PropertiesUtils.getInt("webDriverPool.minPoolSize")
                    ,PropertiesUtils.getInt("webDriverPool.maxPoolSize"),PropertiesUtils.getInt("webDriverPool.timeout"));
        }
        WebDriver driver = webDriverPool.getFree();
        return driver;
    }
    public static synchronized void freeDriver(WebDriver webDriver){
        webDriverPool.toFree(webDriver);
    }
}
