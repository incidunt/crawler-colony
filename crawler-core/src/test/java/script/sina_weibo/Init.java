package script.sina_weibo;
import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class Init implements Script {
    public static void main(String []args) throws Exception {
        Init init = new Init();
        init.work(new Crawler());
    }
    @Override
    public List<Task> work(Crawler crawler) throws Exception {
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        String jsonPath = System.getProperty("user.dir") + "\\crawler-core\\src\\main\\java\\com\\dang\\crawler\\core\\script\\" + "weibo_user.txt";
        //String json = FileUtils.getString(jsonPath);
        //JSONArray array = JSONObject.parseArray(json);
        List<Crawler> crawlerMQList = new ArrayList<>();
        String []array = {"5616413326","5763755594","2654037900"};
        for (int i = 0; i < array.length; i++) {
            String userId = array[i];
            System.out.println("===========================================================================" + i);
            Crawler crawlerMQ = new Crawler();
            crawlerMQ.setUrl("http://weibo.com/u/" + userId + "?topnav=1&wvr=6&topsug=1");
            crawlerMQ.put("userId", userId);
            crawlerMQList.add(crawlerMQ);
        }
        ArrayList<Task> jobList = new ArrayList<Task>();
        jobList.add(new Task(crawlerMQList, new Page()));
        return jobList;
    }
}