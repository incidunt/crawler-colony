package com.dang.crawler.core.parser.utils;

import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duang on 2017/4/26.
 */
public class XpathUtils {
    private static Logger logger = LoggerFactory.getLogger(XpathUtils.class);
    public static TagNode parser(String xml){
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode tagNode = htmlCleaner.clean(xml);
        return tagNode;
    }
    public static Object[] xpath(String xpath, String xml)  {
        return xpath(xpath,parser(xml));
    }
    public static Object[] xpath(String xpath, TagNode tagNode) {
        Object[] objarr = {};
        if(StringUtils.isNotBlank(xpath)) {
            String[] xPaths = xpath.split("\\|\\|");
            for(String xPath : xPaths) {
                try {
                    objarr = tagNode.evaluateXPath(xPath.trim());
                } catch (Exception e) {
                    logger.error("解析失败："+xPath,e);
                }
                if (null!=objarr&&objarr.length>0){
                    break;
                }
            }
            if(null!=objarr && objarr.length>0) {
                return objarr;
//                for (Object obja : objarr) {
//                    if(obja instanceof  String)
//                        list.add(obja.toString());
//                    else if(obja instanceof TagNode){
//                        TagNode tna = (TagNode) obja;
//                        list.add(tna.getText().toString());
//                    }else {
//                        list.add(obja.toString());
//                    }
//                }
            }
        }
        return objarr;
    }
    public static Base xpath(String xpath, Base base){
        switch (base.getStatus()){
            case string:return xpath(xpath,base.getStringList());
            case jsoup:return xpath(xpath,base.getElements());
            case xpath:return xpath(xpath,base.getXpathTagNodes());
        }
        return null;
    }

    public static Base xpath(String xpath, TagNode[] xpathTagNodes) {
        if(xpathTagNodes==null||xpathTagNodes.length==0){
            return new Base("");
        }
        List<String> strings = new ArrayList<>();
        List<TagNode> tagNodes = new ArrayList<>();
        for(TagNode tagNode:xpathTagNodes){
            Object[] objects = xpath(xpath, tagNode);
            for(Object obj :objects){
                if(obj instanceof TagNode){
                    return new Base((TagNode[]) objects);
                }else if(obj instanceof String){
                    strings.add(obj.toString());
                }else {

                }
            }
        }
        return new Base(strings);
    }

    public static Base xpath(String xpath, Elements elements) {
        List<String> strings = new ArrayList<>();
        List<TagNode> tagNodes = new ArrayList<>();
        for(Element e:elements){
            Object[] objects = xpath(xpath, e.toString());
            for(Object obj :objects){
               if(obj instanceof TagNode){
                    return new Base((TagNode[]) objects);
                }else if(obj instanceof String){
                    strings.add(obj.toString());
                }
            }
        }
        return new Base(strings);
    }

    public static Base xpath(String xpath, List<String> stringList) {
        List<String> strings = new ArrayList<>();
        List<TagNode> tagNodes = new ArrayList<>();
        for(String str:stringList){
            Object[] objects = xpath(xpath, str);
            for(Object obj :objects){
                if(obj instanceof String){
                    strings.add(obj.toString());
                }else if(obj instanceof TagNode){
                    return new Base((TagNode[])objects);
                }
            }
        }
        return new Base(strings);
    }
}
