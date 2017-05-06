package   script.sina_weibo   ;


import com.dang.crawler.resources.bean.core.CrawlerJob;
import com.dang.crawler.resources.bean.core.CrawlerMQ;
import com.dang.crawler.resources.bean.core.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class Init implements CrawlerJob {
    @Override
    public List<Job> work(CrawlerMQ mq) throws Exception {
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        String jsonPath = System.getProperty("user.dir") + "\\crawler-core\\src\\main\\java\\com\\dang\\crawler\\core\\script\\" + "weibo_user.txt";
        //String json = FileUtils.getString(jsonPath);
        //JSONArray array = JSONObject.parseArray(json);
        List<CrawlerMQ> crawlerMQList = new ArrayList<>();
        String []array = {"5616413326","5763755594","2654037900"};
        for (int i = 0; i < array.length; i++) {
            String userId = array[i];
            System.out.println("===========================================================================" + i);
            CrawlerMQ crawlerMQ = new CrawlerMQ();
            crawlerMQ.getRequest().setUrl("http://weibo.com/u/" + userId + "?topnav=1&wvr=6&topsug=1");
            crawlerMQ.getExtending().put("userId", userId);
            crawlerMQList.add(crawlerMQ);
        }
        ArrayList<Job> jobList = new ArrayList<Job>();
        jobList.add(new Job(crawlerMQList, new Page()));
        return jobList;
    }
}
