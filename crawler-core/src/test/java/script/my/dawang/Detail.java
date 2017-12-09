package script.my.dawang;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.parser.Parser;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.script.tools.DB;
import com.dang.crawler.core.script.tools.Fetch;

/**
 * Created by mi on 2017/5/17.
 */
public class Detail implements Script {
    private static Set<String> set = new HashSet<>();
    @Override
    public List<Task> work(Crawler crawler, Job job) throws Exception {
        try {
            Page page = Fetch.fetch(crawler);
            String grade = Parser.html(page.getContent()).jsoup(".jg1 .font1:eq(1)").toString();
            if (grade.length() > 0) {
                crawler.put("grade", grade);
            }
        } catch (Exception e) {
        }
        try {
            Page page2 = Fetch.fetch(new Crawler("http://www.bole.name/?k=" + crawler.get("phone")));
            String grade2 = Parser.html(page2.getContent()).jsoup("#price_show").toString();
            if (grade2.length() > 0) {
                crawler.put("grade2", grade2);
            }
        } catch (Exception e) {

        }
        try {
            Page page2 = Fetch.fetch(new Crawler("http://mobile.9om.com/188115/" + crawler.get("phone") + ".html"));
            String grade2 = Parser.html(page2.getContent()).jsoup(".item .rows:eq(3)").regex("\\d+").get(0);
            if (grade2.length() > 0) {
                crawler.put("grade3", grade2);
            }
        } catch (Exception e) {
        }

        DB.insert("phone_list3", "phone", crawler.getInfo());
        return null;
    }
}
