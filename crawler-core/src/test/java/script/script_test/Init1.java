package script.script_test;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.norm.WebWork;
import com.dang.crawler.core.script.tools.Fetch;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dang on 17-5-10.
 */
public class Init1 implements Script {
    public static void main(String []args) throws Exception {

    }


    @Override
    public List<Task> work(final Crawler crawler) throws Exception {
        Page s = Fetch.fetch(crawler);
        log.info("page"+s.getContent());
        Fetch.webWork(new WebWork() {
            @Override
            public void work(WebDriver webDriver) throws Exception {
                System.out.println("xxxxxxxxxxxxxxx");
            }
        });
        return null;
    }
}
