package script.my.dawang;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import script.douban.main.ItemList;

import java.util.ArrayList;
import java.util.List;

/**  豆瓣电影排行
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
        for(int i = 0; i<100000 ; i++) {
            Crawler crawler1 = new Crawler("https://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=11&cityCode=110&monthFeeLimit=0&groupKey=85236889&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=1512270939091");
            crawlerList.add(crawler1);
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new Detail()));
        return results;
    }
}
