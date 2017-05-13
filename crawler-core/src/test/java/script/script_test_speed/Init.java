package script.script_test_speed;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 2017/5/12.
 */
public class Init implements Script{
    public static void main(String []args) throws Exception {
        Init init = new Init();
        init.work(new Crawler());
    }
    @Override
    public List<Task> work(Crawler crawler) throws Exception {
        crawler.setUrl("http://news.baidu.com/?tn=news");
        List<Crawler> crawlerList = new ArrayList<>();
        crawlerList.add(crawler);
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(crawlerList,new FastCrawler()));
        return tasks;
    }
}
