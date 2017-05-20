package com.dang.crawler.core.fetcher;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.fetcher.service.PageService;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by mi on 2017/5/18.
 */
public class PageServiceTest {
    @Test
    public void test() throws IOException {
        Crawler crawler = new Crawler();
        crawler.setUrl("https://www.51bccf.com/E58XVQkYMWr4UXbQbLnzZr-TU5cuxTeyDtI41pEPeVTZecmchJCvX1RvymfkPxNm/E5887");
        crawler.setBody("divGuideStep2%3AsmsVerifyCodePanel%3ActnMsg%3AlnkChange=x&divGuideStep2%3AsmsVerifyCodePanel%3AtxtPhoneNumber=17060910208&divGuideStep2%3AsmsVerifyCodePanel%3AtxtVerifycode=%E9%AA%8C%E8%AF%81%E7%A0%81");
        crawler.putHeader("Cookie","JSESSIONID=aa82ea48b39a5e7cd4dfb7d2369f");
        Page page = PageService.fetcher(crawler);
        System.out.println(page.getContent());
    }
}
