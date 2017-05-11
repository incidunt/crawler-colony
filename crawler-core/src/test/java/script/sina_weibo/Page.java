package script.sina_weibo;
import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.parser.utils.Base;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.fetcher.file.Downloader;
import com.dang.crawler.core.fetcher.service.WebDriverFactory;
import com.dang.crawler.core.script.norm.Task;
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
public class Page implements Script {
    public List<Task> work(Crawler crawler) throws Exception {
        System.out.println(crawler);
        WebDriver web = WebDriverFactory.makeWebDriver(WebDriverFactory.Driver.chrome);
        web.get(crawler.getUrl());
        for(int i = 0;i<5;i++) {
            ((JavascriptExecutor) web).executeScript("document.body.scrollTop+=10000");
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
            map.put("userID", crawler.get("userId").toString());
            map.put("userName", crawler.get("userName").toString());
            dbList.add(map);
        }
        MongoDB.insert(dbList,"sina_weibo");
        return null;
    }
}