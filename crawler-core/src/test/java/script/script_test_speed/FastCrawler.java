package script.script_test_speed;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.parser.Parser;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.Fetch;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dang on 2017/5/12.
 */
public class FastCrawler implements Script{
    @Override
    public List<Task> work(Crawler crawler,Job job) throws Exception {

        Page p = Fetch.fetch(crawler);
        List<Crawler> crawlerList = new ArrayList<>();
        for(String url : Parser.html(p.getContent()).jsoup("a").attrList("href")){
            Crawler nc = new Crawler();
            nc.setUrl(url);
            crawlerList.add(nc);
        }
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(crawlerList,new FastCrawler()));
        return tasks;
    }
}
