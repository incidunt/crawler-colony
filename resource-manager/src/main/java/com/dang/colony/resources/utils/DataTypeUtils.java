package com.dang.colony.resources.utils;

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
        StringBuffer stringBuffer =new StringBuffer();
        for(Object obj:array){
           stringBuffer.append(obj.toString()+split);
        }
        return stringBuffer.toString();
    }
    public static <T>String listToString(List<T> list,String split){
        if(list==null){
            return "";
        }
        StringBuffer stringBuffer =new StringBuffer();
        if(list.size()==1){
            return list.get(0).toString();
        }
        for(T i:list){
            stringBuffer.append(i.toString()+split);
        }
        return stringBuffer.toString();
    }
}