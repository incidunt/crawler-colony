package com.dang.crawler.core.parser.utils;

import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duang on 2017/4/26.
 */
public class JsoupUtils {
    public static Document parser(String html){
        Document doc = org.jsoup.Jsoup.parse(html);
        return doc;
    }
    public static Elements jsoup(String jsoupQuery, String doc){
        return jsoup(jsoupQuery,parser(doc));
    }
    public static Elements jsoup(String jsoupQuery, Element doc){
        Elements elements = null;
        String[] jsoupParamArray = jsoupQuery.split("\\|\\|");
        List<Element> resultList = new ArrayList<>();
        for(String jsoupParam : jsoupParamArray) {
            if(elements ==null){
                elements =doc.select(jsoupParam);
            }else {
                elements.addAll(doc.select(jsoupParam));
            }
        }
        return elements;
    }
    public static Base jsoup(String jsoup, List<String> stringList) {
        Elements elements = null;
        for(String str:stringList){
            if(elements==null){
                elements = JsoupUtils.jsoup(jsoup, str);
            } else{
                elements.addAll(JsoupUtils.jsoup(jsoup, str));
            }
        }
        return new Base(elements);
    }
    public static Base jsoup(String jsoup, Elements elements) {
        Elements result = null;
        for(Element element:elements){
            if(result==null){
                elements = JsoupUtils.jsoup(jsoup, element);
            } else{
                elements.addAll(JsoupUtils.jsoup(jsoup,element));
            }
        }
        return new Base(elements);
    }

    public static Base jsoup(String jsoup, TagNode[] xpathTagNodes) {
        Elements elements = null;
        for(TagNode tagNode:xpathTagNodes){
            if(elements==null){
                elements = JsoupUtils.jsoup(jsoup, tagNode.getText().toString());
            } else{
                elements.addAll(JsoupUtils.jsoup(jsoup, tagNode.getText().toString()));
            }
        }
        return new Base(elements);
    }
}
