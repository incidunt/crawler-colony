package script.p2p.bianmintou;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.mysql.model.Keyword;

import java.util.ArrayList;
import java.util.List;

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
        crawler.setUrl("https://www.bianmintou.com/index.php?ctl=ajax&act=check_field&fhash=kkOsLAvxdcbpqJtHbTuyjnhKRAluQxOoqbjGsVRjGSODpBlaEh");
        crawler.setBody("");
        Page page = Fetch.fetch(crawler);
        List<Crawler> crawlerList = new ArrayList<>();
        for(Keyword keyword : DB.getKeyWorld(0,100,crawler,job)){
            Crawler newCrawler = new Crawler();
            newCrawler.setUrl("https://www.bbuwin.com/ajaxCheckCellphone.do");
            newCrawler.setBody("paramMap.cellphone="+keyword.getKeyword());
            newCrawler.put("phone",keyword.getKeyword());
            newCrawler.putHeader("Cookie",page.getHeader("Set-Cookie"));
            crawlerList.add(newCrawler);
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new Discern()));
        return results;
    }
}
