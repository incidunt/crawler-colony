package com.dang.crawler.core.fetcher;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.fetcher.bean.Page;
import com.dang.crawler.core.script.tools.Fetch;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 2017/6/1.
 */
public class FiddlerTool {

    public static void main(String []args) throws IOException {
       fiddler();
    }
    static String url = "http://www.dingdingjinfu.com/newUser/checkPhoneOrMail.ding";
    static String body = "param=17060910207";
    static String header="";
    private static void fiddler() throws IOException {
        Crawler crawler = new Crawler(url,body);
        String[] lines = header.split("\n|\t|\t\n");
        String key="",value = "";
        List<String> headList = new ArrayList<>();
        for(String line:lines){
            line = line.trim();
            line = line.replaceAll("\n|\t|\t\n","");
            if(StringUtils.isEmpty(line)) continue;
            if(iskey(line)){
                if(StringUtils.isNotEmpty(key)) {
                    crawler.putHeader(key, value);
                    headList.add(key);
                }
                value="";
                key = line;
            }else {
                value+=line;
            }
        }
        crawler.getHeader().remove("Content-Length");
        crawler.getHeader().remove("If-Modified-Since");
        Page page = Fetch.fetch(crawler);
        System.out.println(page.getContent());
        for(String h:headList){
            String v = crawler.getHeader().get(h);
            crawler.getHeader().remove(h);
            Page npage = Fetch.fetch(crawler);
            if(npage.getContent().length()!=page.getContent().length()){
                crawler.getHeader().put(h,v);
                System.out.println(h+":"+v);
            }
        }
    }
    private static boolean iskey(String line) {
        String [] keys ={"Accept","Accept-Encoding","Accept-Language","Connection","Content-Length","Content-Type","Cookie","Cache-Control"
                ,"Host","Referer","User-Agent","Wicket-Ajax","Wicket-Ajax-BaseURL","X-Requested-With","AJAX","If-Modified-Since"};
        for(String key:keys){
            if(key.equals(line)){
                return true;
            }
        }
        return false;
    }
}
