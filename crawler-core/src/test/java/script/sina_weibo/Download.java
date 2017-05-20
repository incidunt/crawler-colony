package script.sina_weibo;


import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;
import java.util.List;

/**
 * Created by mi on 2017/5/12.
 */
public class Download implements Script {
    @Override
    public List<Task> work(Crawler crawler, Job job) throws Exception {
        Page page = Fetch.fetch(crawler);
        String url = crawler.getUrl();
        String path = url.substring(url.lastIndexOf("/"), url.length());
        DB.saveFile(path,page.getResponseBytes());
        return null;
    }
}
