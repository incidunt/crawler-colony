package script.sina_weibo;


import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.resources.mysql.model.Keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dang on 17-5-10.
 */
public class Init implements Script {
    public static void main(String []args){
        try {
            Job job = new Job();
            job.setProjectId(1);
            new Init().work(new Crawler(),job);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Task> work(final Crawler crawler, Job job) throws Exception {
        List<Crawler> crawlerMQList = new ArrayList<>();
        List<Keyword> keywordList = DB.getKeyWorld(0,10,crawler,job);
        for (Keyword keyword :keywordList) {
            Crawler crawlerMQ = new Crawler();
            crawlerMQ.setUrl("http://weibo.com/u/" + keyword.getKeyword() + "?topnav=1&wvr=6&topsug=1");
            crawlerMQ.put("userId", keyword.getKeyword());
            crawlerMQList.add(crawlerMQ);
        }
        ArrayList<Task> jobList = new ArrayList<Task>();
        jobList.add(new Task(crawlerMQList, new ImgList()));
        return jobList;
    }
}
