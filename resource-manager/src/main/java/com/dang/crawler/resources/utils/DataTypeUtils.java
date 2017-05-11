package com.dang.crawler.resources.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangqihe on 2016/12/2.
 */
public class DataTypeUtils{
    //public static <T> void sort(T[] a, Comparator<? super T> c)
    public static <T> List arrayToList(T []array){
        List<T> list = new ArrayList<T>();
        for(T i:array){
            list.add(i);
        }
        return list;
    }
    public static String arrayToString(Object []array, String split){
        if(array==null||array.length==0){
            return "";
        }
        StringBuffer stringBuffer =new StringBuffer();
        for(int index = 0;index<array.length-1;index++){
           stringBuffer.append(array[index].toString()+split);
        }
        stringBuffer.append(array[array.length-1].toString());
        return stringBuffer.toString();
    }
    public static <T>String listToString(List<T> list,String split){
        if(list==null||list.size()==0){
            return "";
        }
        StringBuffer stringBuffer =new StringBuffer();
        for(int index = 0;index<list.size()-1;index++){
            stringBuffer.append(list.get(index).toString()+split);
        }
        stringBuffer.append(list.get(list.size()-1).toString());
        return stringBuffer.toString();
    }
}