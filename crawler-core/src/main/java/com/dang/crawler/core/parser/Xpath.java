package com.dang.crawler.core.parser;

import com.dang.crawler.resources.utils.DataTypeUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dang on 17-5-10.
 */
public class Xpath implements Iterable<Xpath>{
    private TagNode []tagNodeArray = {};
    private List<String> stringList = new ArrayList<>();
    public Xpath(String xml){
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        tagNodeArray = new TagNode[]{htmlCleaner.clean(xml)};
    }
    public Xpath(List<String> stringList){
        this.stringList = stringList;
    }
    public Xpath(TagNode tagNode){
        tagNodeArray = new TagNode[]{tagNode};
    }
    public Xpath(TagNode []tagNodeArray){
        this.tagNodeArray = tagNodeArray;
    }

    public Xpath xpath(String xpath){
        List<TagNode> nextTagNodeList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        if(stringList.size()>0&&tagNodeArray.length==0){
            HtmlCleaner htmlCleaner = new HtmlCleaner();
            tagNodeArray = new TagNode[stringList.size()];
            for(int index = 0;index<stringList.size();index++){
                tagNodeArray[index] = htmlCleaner.clean(stringList.get(index));
            }
        }
        for(TagNode tagNode : tagNodeArray) {
            Object[] objects = new Object[0];
            try {
                objects = tagNode.evaluateXPath(xpath);
            } catch (XPatherException e) {
                e.printStackTrace();
                continue;
            }
            for (Object obj : objects) {
                if (obj instanceof TagNode) {
                    nextTagNodeList.add((TagNode) obj);
                } else if (obj instanceof String) {
                    stringList.add((String) obj);
                } else {
                    stringList.add(obj.toString());
                }
            }
        }
        if(nextTagNodeList.size()>0) {
            TagNode []tagNodes = new TagNode[nextTagNodeList.size()];
            for(int index=0;index<nextTagNodeList.size();index++){
                tagNodes[index] = nextTagNodeList.get(index);
            }
            return new Xpath(tagNodes);
        }else {
            return new Xpath(stringList);
        }
    }


    @Override
    public Iterator<Xpath> iterator() {
        List<Xpath> list = new ArrayList<>();
        for(TagNode tagNode:tagNodeArray){
            list.add(new Xpath(tagNode));
        }
        return list.iterator();
    }

    @Override
    public String toString() {
        if(tagNodeArray!=null&&tagNodeArray.length>0){
            StringBuffer stringBuffer =new StringBuffer();
            for(int index = 0;index<tagNodeArray.length-1;index++){
                stringBuffer.append(tagNodeArray[index].getText()+"||");
            }
            stringBuffer.append(tagNodeArray[tagNodeArray.length-1].getText());
            return stringBuffer.toString();
        }
        return DataTypeUtils.listToString(stringList,"||");
    }
}
