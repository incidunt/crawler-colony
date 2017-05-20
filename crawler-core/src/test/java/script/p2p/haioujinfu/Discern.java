package script.p2p.haioujinfu;

import com.alibaba.fastjson.JSONObject;
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
        //不存在{"status":0,"info":"\u5bf9\u5e94\u4f1a\u5458\u4e0d\u5b58\u5728"}
        //存在{"status":1}
        Page page = Fetch.fetch(crawler);
        Map map = new HashMap();
        map.put("phone",crawler.get("phone"));
        map.put("source","haioujinfu");
        JSONObject json = null;
        try {
             json = Parser.json(page.getContent());
        }catch (Exception e){
            log.info(page.getContent());
        }
        if(json.getInteger("status").equals(0)){
            map.put("registered","0");
        }else if(json.getInteger("status").equals(1)){
            map.put("registered","1");
        }else {
            log.info(page.getContent());
        }
        DB.insert("p2pblack_list","phone",map);
        return null;
    }
}
