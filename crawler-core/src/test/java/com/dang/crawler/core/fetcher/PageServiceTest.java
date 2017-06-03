package com.dang.crawler.core.fetcher;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.fetcher.service.PageService;
import com.dang.crawler.core.parser.Parser;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by mi on 2017/5/18.
 */
public class PageServiceTest {
    @Test
    public void test() throws IOException {
        Crawler crawler = new Crawler();
        crawler.setUrl("https://www.darenloan.com/rf/support/validate/phoneAccount");
        crawler.setBody("[{\"validate\":\"17060910209\"}]");
        //crawler.putHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                                  //acw_tc=AQAAAAVc5ky/aQMAh0snagFpp7j3y/Oe; JSESSIONID=1b049f58435e0280d9da58e3af70
//        crawler.putHeader("Cookie", "acw_tc=AQAAAKj9+FJBBgYAh0snanb3XWJd1+Sb; JSESSIONID=1f638ff7f84d3dd49ca1274e3b77");
//        crawler.putHeader("Wicket-Ajax-BaseURL","user/retrievepwphoneidentifypage.html?0");
        Page page = PageService.fetcher(crawler);
        System.out.println(page.getContent());
        String out = Parser.html(page.getContent()).jsoup("field").attr("isexist");
        System.out.println(out);
    }
}
