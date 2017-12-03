package script.douban.main;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.resources.mysql.model.Keyword;

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
        String[] yearIds = {"45837913","42975662","37815319","3401345","1765813","943009","226207"};
        //https://www.douban.com/doulist/45837913/?start=0&sort=seq&sub_type=
        List<Crawler> crawlerList = new ArrayList<>();
        int year = 2017;
        for(String yearid : yearIds) {
            for(int start = 0; start < 25* 10; start += 25){
                String url = "https://www.douban.com/doulist/"+yearid+"/?start="+start+"&sort=seq&sub_type=";
                Crawler newCrawler = new Crawler(url);
                newCrawler.put("year",year);
                newCrawler.putHeader("Cookie","bid=j71JGYscnPA; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1511015871%2C%22https%3A%2F%2Fwx.qq.com%2F%22%5D; _pk_id.100001.8cb4=ed40e6ecd42d233a.1497760164.8.1511019831.1501921918.; __utma=30149280.23581734.1497760167.1511015876.1511021720.16; __utmz=30149280.1511015876.15.11.utmcsr=wx.qq.com|utmccn=(referral)|utmcmd=referral|utmcct=/; _vwo_uuid_v2=7E50844A8A04C56D5FE623BF512BDD17|273b530b1b904c32d697757830e58215; ue=\"dangfugui@163.com\"; __utmv=30149280.16245; __utmc=30149280; ap=1; __utmb=30149280.0.10.1511021720; ps=y; dbcl2=\"162455034:39rLZ+xPiDs\"; ck=k6z4; push_noty_num=0; push_doumail_num=0");
//                newCrawler.putHeader("","");
//                newCrawler.putHeader("","");
//                newCrawler.putHeader("","");
//                newCrawler.putHeader("","");
                crawlerList.add(newCrawler);
            }
            year -- ;
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new ItemList()));
        return results;
    }
}
