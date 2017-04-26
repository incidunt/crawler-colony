package com.dang.crawler.core.bean;

import com.dang.crawler.core.tool.Jsoup;
import com.dang.crawler.core.tool.Regex;
import com.dang.crawler.core.tool.Xpath;
import org.htmlcleaner.TagNode;
import org.jsoup.select.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by duang on 2017/4/26.
 */
public class Base {
    private List<String> stringList = new ArrayList<>();
    private Elements elements;
    private TagNode[] xpathTagNodes;
    private Status status = Status.string;
    public static enum Status{string,jsoup,xpath};

    public Base(String page){
        status = Status.string;
        stringList.add(page);
    }
    public Base(List<String> list){
        status = Status.string;
        stringList = list;
    }
    public Base(Elements elements){
        status = Status.jsoup;
        this.elements = elements;
    }
    public Base(TagNode[] objects) {
        xpathTagNodes = objects;
        status = Status.xpath;
    }

    public Base xpath(String xpath){
        switch (this.getStatus()){
            case string:return Xpath.xpath(xpath,this.getStringList());
            case jsoup:return Xpath.xpath(xpath,this.getElements());
            case xpath:return Xpath.xpath(xpath,this.getXpathTagNodes());
            default:return new Base("");
        }
    }
    public Base jsoup(String jsoup){
        List<Base> list = new ArrayList<>();
        switch (status) {
            case string:return Jsoup.jsoup(jsoup, stringList);
            case jsoup:return Jsoup.jsoup(jsoup, elements);
            case xpath:return Jsoup.jsoup(jsoup, xpathTagNodes);
            default:
                return new Base("");
        }
    }
    public Base regex(String regex){
        switch (status) {
            case string:return Regex.regex(regex, stringList);
            case jsoup:return Regex.regex(regex, elements);
            case xpath:return Regex.regex(regex, xpathTagNodes);
            default:
                return new Base("");
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public TagNode[] getXpathTagNodes() {
        return xpathTagNodes;
    }

    public void setXpathTagNodes(TagNode[] xpathTagNodes) {
        this.xpathTagNodes = xpathTagNodes;
    }
}
