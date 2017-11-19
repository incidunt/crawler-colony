package script.douban.main;

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
public class Detail implements Script {
    @Override
    public List<Task> work(Crawler crawler, Job job) throws Exception {
        Thread.sleep(2000);
        Page page = Fetch.fetch(crawler);
        String duanPing = Parser.html(page.getContent()).jsoup(".mod-hd .pl:eq(1)").toString().trim().split(" ")[2];
        String yingPing = Parser.html(page.getContent()).jsoup(".reviews .pl:eq(0)").toString().trim().split(" ")[1];
        crawler.put("duanPing",duanPing);
        crawler.put("yingPing",yingPing);
        crawler.put("url",crawler.getUrl());
        DB.insert("douban_list","name",crawler.getInfo());
        return null;
    }
}
