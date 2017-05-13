package com.dang.crawler.core.script;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dang.crawler.core.fetcher.file.Downloader;
import com.dang.crawler.core.fetcher.service.WebDriverFactory;
import com.dang.crawler.core.parser.utils.Base;
import com.dang.crawler.resources.mongodb.MongoDB;
import com.dang.crawler.resources.utils.DateUtils;
import com.dang.crawler.resources.utils.FileUtils;
import org.bson.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by duang on 2017/4/27.
 */
public class SinaWeibao {
    public static void main(String []args) throws InterruptedException {
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        String jsonPath = System.getProperty("user.dir") + "\\crawler-core\\src\\test\\java\\com\\dang\\crawler\\core\\script\\" + "weibo_user.txt";
        String json= FileUtils.toString(jsonPath);
        JSONArray array = JSONObject.parseArray(json);
        for(int i =0;i<array.size();i++){
            JSONObject  jsonObject=  JSONObject.parseObject(array.getString(i));
            String name = jsonObject.getString("name");
            String userId = jsonObject.getString("user");
            System.out.println("==========================================================================="+i);
            try {
                getOneUser(userId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            Thread.sleep(10000);
        }
    }
    public static void getOneUser(String userID) throws InterruptedException, UnknownHostException {
        WebDriver web = WebDriverFactory.makeWebDriver(WebDriverFactory.Driver.phantomjs);
        web.get("http://weibo.com/u/"+userID+"?topnav=1&wvr=6&topsug=1");
//        WebElement input = web.findElement(By.cssSelector(".gn_search_v2 .W_input"));
//        input.sendKeys("");
        for(int i = 0;i<5;i++) {
            ((JavascriptExecutor) web).executeScript("document .body .scrollTop+=10000");
            Thread.sleep(5000);
        }
        Base base = new Base(web.getPageSource());
        web.close();
        List<Document> dbList = new ArrayList<>();
        for(Base item:base.jsoup(".WB_detail").list()){
            Document map = new Document();
            map.put("userName",item.jsoup(".W_f14.W_fb.S_txt1").string());
            map.put("time",item.jsoup("a.S_txt2[title]").string());
            map.put("content",item.jsoup(".WB_text").string());
            List<String> imageURLList = new ArrayList<>();
            for(Base li : item.jsoup("li img").list()){
                String url = li.attribute("src");
                imageURLList.add(url);
                Downloader.getInstance().addURL(url);
            }
            map.put("imageList",imageURLList);
            map.put("_crawler_date", DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
            map.put("userID", userID);
            dbList.add(map);
        }
        MongoDB.insert(dbList,"sina_weibo");
    }
}
