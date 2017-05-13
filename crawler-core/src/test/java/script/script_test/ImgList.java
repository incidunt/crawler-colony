package script.script_test;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.parser.utils.Base;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.mongodb.MongoDB;
import com.dang.crawler.resources.utils.DateUtils;
import org.bson.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-5-10.
 */
public class ImgList implements Script {

    @Override
    public List<Task> work(final Crawler crawler) throws Exception {
        WebDriver web = Fetch.getWebDriver();
        web.get(crawler.getUrl());
        Thread.sleep(5000);
        for(int i = 0;i<5;i++) {
            ((JavascriptExecutor) web).executeScript("document.body.scrollTop+=10000");
            Thread.sleep(2000);
        }
        Base base = new Base(web.getPageSource());
        Fetch.freeDriver(web);
        List<Map> dbList = new ArrayList<>();
        List<Crawler> crawlers = new ArrayList<>();
        for(Base item:base.jsoup(".WB_detail").list()){
            Document map = new Document();
            map.put("userName",item.jsoup(".W_f14.W_fb.S_txt1").string());
            map.put("time",item.jsoup("a.S_txt2[title]").string());
            map.put("content",item.jsoup(".WB_text").string());
            List<String> imageURLList = new ArrayList<>();
            for(Base li : item.jsoup("li img").list()){
                String url = li.attribute("src");
                imageURLList.add(url);
                Crawler nextCrawler = new Crawler();
                nextCrawler.setUrl(url);
                crawlers.add(nextCrawler);
                //Downloader.getInstance().addURL(url);
            }
            map.put("imageList",imageURLList);
            map.put("_crawler_date", DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
            map.put("userID", crawler.get("userId").toString());
            //map.put("userName", crawler.get("userName").toString());
            dbList.add(map);
        }
        DB.insert("script_task_imgList",dbList);
        List<Task> result = new ArrayList<>();
        result.add(new Task(crawlers,new Download()));
        return result;
    }
}
