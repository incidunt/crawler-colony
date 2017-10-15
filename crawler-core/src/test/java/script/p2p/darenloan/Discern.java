package script.p2p.darenloan;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import org.apache.commons.lang3.StringUtils;

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
        if(StringUtils.isEmpty(page.getContent())){
            map.put("registered","0");
        }else if(page.getContent().length()<100&&page.getContent().contains("该手机号码已注册")){
            map.put("registered","1");
        }else {
            log.error("改版内容:"+page.getContent());
        }
        DB.insert("p2pblack_list","phone",map);
        return null;
    }
}
