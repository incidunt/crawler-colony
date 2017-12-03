package script.my.grade;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.dao.TableMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        TableMapper tableMapper = (TableMapper) ApplicationContext.getBean("tableMapper");
        List<Crawler> crawlerList = new ArrayList<>();
        for(int i = 0;i<10000;i++) {
            int size = 1000;
            List<Map<String, Object>> list = tableMapper.select("crawler_phone_grade_list", i * size, size);
            for (Map<String, Object> map : list) {
                String phone = (String) map.get("phone");
                Crawler nc = new Crawler("http://www.haomagujia.com/" + phone.trim());
                nc.put("phone",phone.trim());
                crawlerList.add(nc);
            }
            if(list.size() != size) break;
        }
        ArrayList<Task> results = new ArrayList<Task>();
        results.add(new Task(crawlerList,new Detail()));
        return results;
    }
}
