package script.script_test;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dang on 17-5-10.
 */
public class Init implements Script {
    public static void main(String []args) throws Exception {
        Init init = new Init();
        Crawler crawler = new Crawler();
        crawler.setUrl("http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=app");
        init.work(crawler);
    }


    @Override
    public List<Task> work(final Crawler crawler) throws Exception {
        List<Crawler> crawlerMQList = new ArrayList<>();
        String []array = {"5616413326","5763755594","2654037900"};
        for (int i = 0; i < array.length; i++) {
            String userId = array[i];
            log.info("===========================================================================" +i);
            Crawler crawlerMQ = new Crawler();
            crawlerMQ.setUrl("http://weibo.com/u/" + userId + "?topnav=1&wvr=6&topsug=1");
            crawlerMQ.put("userId", userId);
            crawlerMQList.add(crawlerMQ);
        }
        ArrayList<Task> jobList = new ArrayList<Task>();
        jobList.add(new Task(crawlerMQList, new ImgList()));
        return jobList;
    }
}
