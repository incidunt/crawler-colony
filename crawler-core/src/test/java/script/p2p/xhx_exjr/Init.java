package script.p2p.xhx_exjr;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.mysql.model.Keyword;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        crawler.setUrl("http://new.xhx-exjr.com/user/retrievepwphoneidentifypage.html?3-1.IBehaviorListener.0-body-form-txtUserPhone");
        crawler.setBody("txtUserPhone="+"17060910200");
        Page page = Fetch.fetch(crawler);
        Map<String,String> map = new HashMap();
        map.put("Accept","application/xml, text/xml, */*; q=0.01");
        map.put("Accept-Encoding","gzip, deflate");
        map.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put("Connection","keep-alive");
        map.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        map.put("Host","new.xhx-exjr.com");
        map.put("Referer", "http://new.xhx-exjr.com/user/retrievepwphoneidentifypage.html?2");
        map.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:53.0) Gecko/20100101 Firefox/53.0");
        map.put("Wicket-Ajax-BaseURL","user/retrievepwphoneidentifypage.html?2");
        map.put("X-Requested-With","XMLHttpRequest");
        map.put("Wicket-Ajax","true");
        map.put("Cookie","JSESSIONID=AD9A74C556214C1277C3211159663AD1; Hm_lvt_a36c1138d06a9411d5c5891b06d37686=1495015375,1495021612; Hm_lpvt_a36c1138d06a9411d5c5891b06d37686=1495023496");
        List<Crawler> crawlerList = new ArrayList<>();
        for(Keyword keyword : DB.getKeyWorld(0,100,crawler,job)){
            Crawler newCrawler = new Crawler();
            newCrawler.setHeader(map);
            newCrawler.setUrl("http://new.xhx-exjr.com/user/retrievepwphoneidentifypage.html");
            newCrawler.setBody("txtUserPhone="+keyword.getKeyword());
            newCrawler.put("phone",keyword.getKeyword());
            crawlerList.add(newCrawler);
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new Discern()));
        return results;
    }
}
