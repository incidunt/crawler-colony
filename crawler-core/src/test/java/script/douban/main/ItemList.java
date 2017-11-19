package script.douban.main;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.parser.Jsoup;
import com.dang.crawler.core.parser.Parser;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.Fetch;
import com.dang.crawler.resources.utils.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 2017/5/17.
 */
public class ItemList implements Script {
    @Override
    public java.util.List<Task> work(Crawler crawler, Job job) throws Exception {
        Page page = Fetch.fetch(crawler);
        List<Crawler> crawlers = new ArrayList<>();
        for(Jsoup item : Parser.html(page.getContent()).jsoup(".mod")){
            try {
                Crawler cr = new Crawler(item.jsoup(".title a").attr("href"));
                String[] arr = item.jsoup(".abstract").toString().split(":");
                cr.put("name",item.jsoup(".title a").toString());
                cr.put("start",item.jsoup(".rating_nums").toString());
                cr.put("pingJia",item.jsoup(".rating").toString());
                cr.put("daoYan",arr[1].trim().split(" ")[0]);
                cr.put("zuYan",arr[2].substring(0,arr[2].length()-2).trim());
                cr.put("type",arr[3].trim().split(" ")[0]);
                cr.put("country",arr[4].trim().split(" ")[0]);
                cr.put("releaseYear",arr[5].trim());
                cr.put("comment",item.jsoup(".comment").toString());
                cr.setHeader(crawler.getHeader());
                crawlers.add(cr);
            }catch (Exception e){
                Log.error("解析失败"+item.jsoup(".title a").toString());
            }
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlers,new Detail()));
       // DB.insert("p2pblack_list","phone",map);
        return results;
    }
}
