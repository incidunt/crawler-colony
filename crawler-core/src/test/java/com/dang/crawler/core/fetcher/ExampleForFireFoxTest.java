package com.dang.crawler.core.fetcher;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;

/**
 * Created by mi on 2017/5/2.
 */
public class ExampleForFireFoxTest {
    public static WebDriver driver;


    public static void main(String []args) {
        beforeMethod();
       System.out.println(driver.getPageSource());

    }


    public static void beforeMethod() {

        //Open the URL in Chrome
        File file = new File("D:\\Files\\MyCode\\USE\\chromedriver_win32\\chromedriver_28.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();
        driver.get("http://stackoverflow.com/questions/29238443/warning-the-server-did-not-provide-any-stacktrace-information");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().window().maximize();
    }

    public void afterMethod() {
        //Close the browser
        driver.quit();
    }

}
