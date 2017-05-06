package com.dang.crawler.core.fetcher;

import com.dang.crawler.core.fetcher.service.WebDriverFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class SpringTest {
    //@Resource
    private WebDriverFactory webDriverFactory;
    @Before
    public void setUp() throws Exception {
        webDriverFactory= (WebDriverFactory) new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("webDriverFactory");
    }
    @Test
    public void testQueryByTaskId() {
        System.out.println(webDriverFactory.phantomjsDrivePath);
    }
}
