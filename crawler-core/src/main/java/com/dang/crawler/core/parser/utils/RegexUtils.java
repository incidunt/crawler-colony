package com.dang.crawler.core.parser.utils;

import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duang on 2017/4/26.
 */
public class RegexUtils {
    public static List<String> regex(String regex,String content){
        List<String> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(regex)||StringUtils.isEmpty(content)){
            return resultList;
        }
        int start=0,end=1000;
        if(regex.contains("@[")){
            String param = regex.substring(regex.indexOf("@[")+2,regex.length());
            regex = regex.substring(0,regex.indexOf("@["));
            try {
                String starts = param.substring(0, param.indexOf(":"));
                String ends = param.substring(param.indexOf(":") + 1, param.length() - 1);
                start = Integer.parseInt(starts);
                end = Integer.parseInt(ends);
            }catch (Exception e){

            }
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int index = 0;
        while(matcher.find()) {
            if(index>end){
                break;
            }
            if(index >= start) {
                resultList.add(matcher.group());
            }
            index++;
        }
        return resultList;
    }

    public static Base regex(String regex, List<String> stringList) {
        List<String> list = new ArrayList<>();
        for(String str:stringList){
            list.addAll( regex(regex, str));
        }
        return new Base(list);
    }

    public static Base regex(String regex, Elements elements) {
        List<String> list = new ArrayList<>();
        for(Element element :elements){
            list.addAll( regex(regex, elements.toString()));
        }
        return new Base(list);
    }

    public static Base regex(String regex, TagNode[] xpathTagNodes) {
        List<String> list = new ArrayList<>();
        for(TagNode tagNode :xpathTagNodes){
            list.addAll( regex(regex,tagNode.getText().toString()));
        }
        return new Base(list);
    }
}
