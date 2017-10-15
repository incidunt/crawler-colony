package script.p2p.changjidai;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.parser.Parser;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.mysql.model.Keyword;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dang on 17-5-17.
 */
public class Init implements Script {
    public static void main(String []args) throws Exception {
        Init init =  new Init();
        Job job = new Job();
        job.setProjectId(2);
        init.work(new Crawler(),job);
    }
    @Override
    public List<Task> work(Crawler crawler, Job job) throws Exception {
        crawler.setUrl("http://www.changjidai.com/regist.html?0");
        WebDriver web = Fetch.getWebDriver();
        web.get("http://www.changjidai.com/regist.html?0");
        Thread.sleep(5000);
        Set<Cookie> cookies = web.manage().getCookies();
        StringBuffer cookieSb = new StringBuffer();
        for(Cookie cookie:cookies){
            cookieSb.append(cookie.getName()+"="+cookie.getValue()+";");
        }
        List<Crawler> crawlerList = new ArrayList<>();
        for(Keyword keyword : DB.getKeyWorld(0,100,crawler,job)){
            Crawler newCrawler = new Crawler();
            newCrawler.setUrl("http://www.changjidai.com/regist.html?0-1.IBehaviorListener.0-body-form-txtUserPhone");
            newCrawler.setBody("txtUserPhone="+keyword.getKeyword());
            newCrawler.putHeader("Cookie",cookieSb.toString());
            newCrawler.putHeader("Wicket-Ajax-BaseURL","user/retrievepwphoneidentifypage.html?0");
            newCrawler.put("phone",keyword.getKeyword());
            crawlerList.add(newCrawler);
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new Discern()));
        return results;
    }
}
