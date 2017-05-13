package com.dang.crawler.core.fetcher;

import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by mi on 2017/5/12.
 */
public class FetchTest {

    public static void main(String []args) throws InterruptedException {
       WebDriver web = Fetch.getWebDriver();
//        web.get("http://www.baidu.com/baidu?wd=%CF%C2%D4%D8&tn=monline_4_dg");
//        WebDriver web2 = Fetch.getWebDriver();
//        web2.get("http://www.oschina.net/code/snippet_220184_22956");
//        Thread.sleep(5000);
//        web.
//        System.setProperty("phantomjs.binary.path", PropertiesUtils.getProperty("selenium.phantomjs.driver.path"));
//        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
 //       WebDriver web = new PhantomJSDriver(desiredCapabilities);
        web.get("http://weibo.com/u/5616413326?topnav=1&wvr=6&topsug=1");
        Thread.sleep(5000);
        System.out.println(web.getTitle());
        System.out.println(web.getPageSource().length());
       // System.out.println(web2.getPageSource().length());
       // Fetch.freeDriver(web);
    }
}
