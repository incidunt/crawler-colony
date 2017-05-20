package com.dang.crawler.resources.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dang on 2017/5/9.
 */
public class PropertiesUtils {
    private static Properties properties = createProperties();
    public static String getProperty(String name){
        return properties.getProperty(name);
    }
    public static int getInt(String name){
        return Integer.parseInt(properties.getProperty(name));
    }

    private static Properties createProperties() {
        properties = new Properties();
        String []fileNames = {"db.properties","sysconfig.properties"};
        try {
            for(String file : fileNames) {
                InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(file);
                if(inputStream!=null) {
                    properties.load(inputStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
