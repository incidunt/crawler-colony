package script.p2p.qycfu;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
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
        //不存在["form-validation-field-0",true]
        //存在{"have":true}
        Page page = Fetch.fetch(crawler);
        Map map = new HashMap();
        map.put("phone",crawler.get("phone"));
        map.put("source","passport.trc.com");
        if(page.getContent().trim().equals("[\"form-validation-field-0\",true]")){
            map.put("registered","0");
        }else {
            map.put("registered","1");
            log.info(page.getContent());
        }
        DB.insert("p2pblack_list","phone",map);
        return null;
    }
}
