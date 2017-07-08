package script.sina_weibo;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.parser.*;
import com.dang.crawler.core.parser.Text;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.utils.DateUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.*;

/**
 * Created by dang on 17-5-10.
 */
public class ImgList implements Script {
    public static void main(String []args) throws Exception {
        Crawler crawler = new Crawler();
        crawler.setUrl("http://weibo.com/u/2515047730?topnav=1&wvr=6&topsug=1&is_hot=1");
        new ImgList().work(crawler,new Job());
    }
    @Override
    public List<Task> work(final Crawler crawler,Job job) throws Exception {
        WebDriver web = Fetch.getWebDriver();
        web.get(crawler.getUrl());
        Thread.sleep(2000);
        for(int i = 0;i<5;i++) {
            ((JavascriptExecutor) web).executeScript("document.body.scrollTop+=10000");
            Thread.sleep(2000);
        }
//        Base base = new Base(web.getPageSource());
//        Fetch.freeDriver(web);
//        List<Map> dbList = new ArrayList<>();
//        List<Crawler> crawlers = new ArrayList<>();
//        for(Base item:base.jsoup(".WB_detail").list()){
//            Map map = new HashMap();
//            map.put("userName",item.jsoup(".W_f14.W_fb.S_txt1").string());
//            map.put("time",item.jsoup("a.S_txt2[title]").string());
//            map.put("content",item.jsoup(".WB_text").string());
//            List<String> imageURLList = new ArrayList<>();
//            for(Base li : item.jsoup("li img").list()){
//                String url = li.attribute("src");
//                imageURLList.add(url);
//                Crawler nextCrawler = new Crawler();
//                nextCrawler.setUrl(url);
//                crawlers.add(nextCrawler);
//                //Downloader.getInstance().addURL(url);
//            }
//            map.put("imageList",imageURLList);
//            map.put("_crawler_date", DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
//            //map.put("userID", crawler.get("userId").toString());
//            //map.put("userName", crawler.get("userName").toString());
//            dbList.add(map);
//        }
        Text text = Parser.html(web.getPageSource());
        Fetch.freeDriver(web);
        List<Map> dbList = new ArrayList<>();
        List<Crawler> crawlers = new ArrayList<>();
        for(Jsoup item:text.jsoup(".WB_detail")){
            Map<String,Object> map = new HashMap();
            map.put("userName",item.jsoup(".W_f14.W_fb.S_txt1").toString());
            map.put("time",item.jsoup("a.S_txt2[title]").toString());
            map.put("content",item.jsoup(".WB_text").toString());
            List<String> imageURLList = new ArrayList<>();
            for(Jsoup li : item.jsoup("li img")){
                String url = li.attr("src").toString();
                imageURLList.add(url);
                Crawler nextCrawler = new Crawler();
                nextCrawler.setUrl(url);
                crawlers.add(nextCrawler);
                //Downloader.getInstance().addURL(url);
            }
            map.put("imageList",imageURLList);
            map.put("_crawler_date", DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
            if(crawler.get("userId")!=null) {
                map.put("userId", crawler.get("userId").toString());
            }
            //map.put("userName", crawler.get("userName").toString());
            dbList.add(map);
        }
        DB.insert("script_task_imgList","userId",dbList);
        List<Task> result = new ArrayList<>();
        result.add(new Task(crawlers,new Download()));
        return result;
    }
}
