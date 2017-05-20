package script.p2p._51bccf;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.resources.mysql.model.Keyword;
import com.dang.crawler.resources.utils.PropertiesUtils;

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
        List<Crawler> crawlerList = new ArrayList<>();
        for(Keyword keyword : DB.getKeyWorld(0,100,crawler,job)){
            Crawler newCrawler = new Crawler();
            newCrawler.setUrl("https://www.51bccf.com/regist.page?0-1.IBehaviorListener.0-form-txtUserId");
            newCrawler.setBody("txtUserId="+keyword.getKeyword());
            newCrawler.put("phone",keyword.getKeyword());
            crawlerList.add(newCrawler);
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new Discern()));
        return results;
    }
}
