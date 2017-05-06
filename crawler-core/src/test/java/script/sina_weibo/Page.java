package script.sina_weibo;

import com.dang.crawler.core.bean.Base;
import com.dang.crawler.core.fetcher.file.Downloader;
import com.dang.crawler.core.fetcher.service.WebDriverFactory;
import com.dang.crawler.resources.bean.core.CrawlerJob;
import com.dang.crawler.resources.bean.core.CrawlerMQ;
import com.dang.crawler.resources.bean.core.Job;
import com.dang.crawler.resources.mongodb.MongoDB;
import com.dang.crawler.resources.utils.DateUtils;
import org.bson.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by mi on 2017/5/3.
 */
public class Page implements CrawlerJob {
    public List<Job> work(CrawlerMQ crawlerMQ) throws Exception {
        WebDriver web = WebDriverFactory.getwebDriver(WebDriverFactory.Driver.phantomjs);
        web.get(crawlerMQ.getRequest().getUrl());
//        WebElement input = web.findElement(By.cssSelector(".gn_search_v2 .W_input"));
//        input.sendKeys("");
        for(int i = 0;i<5;i++) {
            ((JavascriptExecutor) web).executeScript("document .body .scrollTop+=10000");
            Thread.sleep(5000);
        }
        Base base = new Base(web.getPageSource());
        web.close();
        List<Document> dbList = new ArrayList<>();
        for(Base item:base.jsoup(".WB_detail").list()){
            Document map = new Document();
            map.put("userName",item.jsoup(".W_f14.W_fb.S_txt1").string());
            map.put("time",item.jsoup("a.S_txt2[title]").string());
            map.put("content",item.jsoup(".WB_text").string());
            List<String> imageURLList = new ArrayList<>();
            for(Base li : item.jsoup("li img").list()){
                String url = li.attribute("src");
                imageURLList.add(url);
                Downloader.getInstance().addURL(url);
            }
            map.put("imageList",imageURLList);
            map.put("_crawler_date", DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
            map.put("userID", crawlerMQ.getExtending().get("userId").toString());
            map.put("userName", crawlerMQ.getExtending().get("userName").toString());
            dbList.add(map);
        }
        MongoDB.insert(dbList,"sina_weibo");
        return null;
    }
}
