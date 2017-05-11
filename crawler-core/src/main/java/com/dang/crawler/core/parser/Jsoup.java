package com.dang.crawler.core.parser;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dang on 17-5-10.
 */
public class Jsoup implements Iterable<Jsoup> {
    private Elements elements = new Elements();
    private String split = "||";
    public Jsoup(List<String> stringList){
        for(String html :stringList) {
            Document doc = org.jsoup.Jsoup.parse(html);
            elements.add(doc);
        }
    }
    public Jsoup(Element element){
        elements.add(element);
    }
    public Jsoup(Elements elements){
        this.elements = elements;
    }

    public Jsoup jsoup(String jsoup) {
        return new Jsoup(elements.select(jsoup));

    }
    public List<String> attrList(String attr){
        List list = new ArrayList();
        for(Element element :elements){
            String value =element.attr(attr);
            if(!StringUtils.isBlank(value)) {
                list.add(element.attr(attr));
            }
        }
        return list;
    }
    public String attr(String attr){
        if(elements.size()==1){
            return elements.get(0).attr(attr);
        }else if(elements.size()==0){
            return "";
        }else {
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0;i<elements.size()-1;i++){
                String value =elements.get(i).attr(attr);
                if(StringUtils.isNotBlank(value)) {
                    stringBuffer.append(value+split);
                }
            }
            stringBuffer.append(elements.get(elements.size()-1));
            return stringBuffer.toString();
        }
    }
    @Override
    public Iterator<Jsoup> iterator() {
        List<Jsoup> list = new ArrayList<>();
        for(Element element:elements){
            list.add(new Jsoup(element));
        }
        return list.iterator();
    }

    @Override
    public String toString() {
       if(elements.size()==1){
           return elements.text();
       }else if(elements.size()>0){
           StringBuffer stringBuffer = new StringBuffer();
           for(int i = 0;i<elements.size()-1;i++){
               String value =elements.get(i).text();
               if(StringUtils.isNotBlank(value)) {
                   stringBuffer.append(value+split);
               }
           }
           stringBuffer.append(elements.get(elements.size()-1));
           return stringBuffer.toString();
       }else {
           return "";
       }
    }
}
