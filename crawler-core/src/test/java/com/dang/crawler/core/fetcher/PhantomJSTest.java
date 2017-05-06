package com.dang.crawler.core.fetcher;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by mi on 2017/5/2.
 */
public class PhantomJSTest {
    @Test
    public void test(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "D:\\Files\\MyCode\\USE\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        // set user-agent
       // caps.setCapability("phantomjs.page.settings.userAgent",
       //         "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:25.0) Gecko/20100101 Firefox/25.0 ");
        // set request headers
       // caps.setCapability("phantomjs.page.customHeaders.user-id", "dang");
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
        WebDriver webDriver = new PhantomJSDriver(caps);
        long lastTime =0,thisTime=0;
        for(int i = 0 ; i<10000 ;i++) {
            webDriver = new PhantomJSDriver(caps);
            webDriver.get("https://www.baidu.com/");
            System.out.print(webDriver.getPageSource().length());
            thisTime = new Date().getTime();
            System.out.println("==="+(thisTime-lastTime));
            lastTime = thisTime;
        }
    }
}
