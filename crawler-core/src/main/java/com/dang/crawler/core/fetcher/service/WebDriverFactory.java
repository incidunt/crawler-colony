package com.dang.crawler.core.fetcher.service;

import com.dang.crawler.resources.utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by mi on 2017/5/3.
 * webDriver工厂
 */
public class WebDriverFactory {

    public static enum Driver{chrome,phantomjs}
    public static WebDriver makeWebDriver(Driver driver){
        switch (driver){
            case chrome: return getChrome();
            case phantomjs: return getPhantomjs();
            default: return null;
        }
    }
    private static WebDriver getChrome() {
        System.setProperty("webdriver.chrome.driver",
                PropertiesUtils.getProperty("selenium.chrome.driver.path"));
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome
        // 创建一个 Chrome 的浏览器实例
        WebDriver driver = new ChromeDriver();
        return driver;
    }
    private static WebDriver getPhantomjs() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PropertiesUtils.getProperty("selenium.phantomjs.driver.path"));
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
        cliArgsCap.add("--proxy-type=socks5");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        //WebDriver webDriver = new PhantomJSDriver(caps);
        WebDriver webDriver = new PhantomJSDriver(caps);
        return webDriver;
    }
}
