package com.dang.crawler.core.script;

import com.dang.crawler.resources.utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by dang on 2017/5/2.
 */
public class ExampleForPhantomjs {
    public static void main(String []args) throws InterruptedException {
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

        // cliArgsCap.add("--proxy-type=socks5");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        //WebDriver webDriver = new PhantomJSDriver(caps);
        //System.setProperty("phantomjs.binary.path",PropertiesUtils.getProperty("selenium.phantomjs.driver.path"))
        WebDriver webDriver = new PhantomJSDriver(caps);
        webDriver.get("http://weibo.com/u/5616413326?topnav=1&wvr=6&topsug=1");
        Thread.sleep(5000);
        System.out.println(webDriver.getPageSource());
        System.out.println(webDriver.getTitle());
        webDriver.close();
        webDriver.quit();
    }
}
