package script.p2p._58hongli;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.parser.Parser;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mi on 2017/5/17.
 */
public class Discern implements Script {
    @Override
    public List<Task> work(Crawler crawler, Job job) throws Exception {
        Page page = Fetch.fetch(crawler);
        Map map = new HashMap();
        map.put("phone",crawler.get("phone"));
        map.put("source",job.getJobId());
        String out = Parser.html(page.getContent()).jsoup("field").attr("isexist").trim();
       if(out.equals("1")){
           map.put("registered", "1");
       }else if(out.equals("0")){
           map.put("registered", "0");
       }else  {
           log.error(page.getContent());
       }
       DB.insert("p2pblack_list","phone",map);
        return null;
    }
}
