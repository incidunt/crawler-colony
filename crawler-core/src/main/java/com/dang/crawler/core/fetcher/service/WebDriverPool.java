package com.dang.crawler.core.fetcher.service;

import com.dang.crawler.core.serivce.ApplicationContext;
import org.openqa.selenium.WebDriver;

/**
 * Created by dang on 17-5-10.
 * webDriver资源池
 */
public class WebDriverPool extends RevolPool<WebDriver> {

    public WebDriverPool(int minPoolSize, int maxPoolSize,int timeOut) {
        super(minPoolSize, maxPoolSize,timeOut);
    }

    @Override
    protected void destroy(WebDriver webDriver) {
        webDriver.close();//此方法是关闭当前窗口，或最后打开的窗口
        webDriver.quit();//执行这个方法后，driver会关闭所有关联窗口
    }

    @Override
    protected WebDriver make() {
        if(ApplicationContext.debug){
            return WebDriverFactory.makeWebDriver(WebDriverFactory.Driver.chrome);
        }else {
            return WebDriverFactory.makeWebDriver(WebDriverFactory.Driver.phantomjs);
        }
    }

}
