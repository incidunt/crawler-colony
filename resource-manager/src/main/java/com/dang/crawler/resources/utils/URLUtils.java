package com.dang.crawler.resources.utils;

/**
 * Created by dangqihe on 2017/3/23.
 */
public class URLUtils {
    /**
     * 转换url内容中的特殊字符
     * @param url url参数
     * @return  url参数
     */
    public static String convert(String url){
        url = url.replace("%","%25");
        url = url.replace("+","%2B");
        url = url.replace(" ","%20");
        url = url.replace("/","%2F");
        url = url.replace("?","%3F");
        url = url.replace("%","%25");
        url = url.replace("#","%23");
        url = url.replace("&","%26");
        url = url.replace("=","%3D");
        url = url.replace("\n","");
        url = url.replace("\r","");
        url = url.replace("\t","");
        return url;
    }
    public static boolean isUrl(String url){

        return true;
    }
}
