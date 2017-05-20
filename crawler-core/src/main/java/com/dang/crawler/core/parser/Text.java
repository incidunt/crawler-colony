package com.dang.crawler.core.parser;

import com.dang.crawler.resources.utils.DataTypeUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dang on 17-5-10.
 */
public class Text implements  Iterable<String>{
    private List<String> textList = new ArrayList<>();
    public Text(String content) {
       textList.add(content);
    }
    public Text(List<String> stringList) {
        textList = stringList;
    }
    public Jsoup jsoup(String jsoup){
        return new Jsoup(textList).jsoup(jsoup);
    }
    public Xpath xpath(String xpath) {
        return new Xpath(textList).xpath(xpath);
    }
    public Text regex(String regex){
        List<String> stringList = new ArrayList<>();
        for(String content:textList) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                stringList.add(matcher.group());
            }
        }
        return new Text(stringList);
    }

    @Override
    public String toString() {
        return DataTypeUtils.listToString(textList,"||");
    }
    @Override
    public Iterator<String> iterator() {
        return textList.iterator();
    }
}
