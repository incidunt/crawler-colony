package com.dang.crawler.resources.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by duang on 2017/4/30.
 */
public class FileUtils {
    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void save(String path,byte[] bytes) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();

        }
        OutputStream out = new FileOutputStream(file);
        out.write(bytes);
        out.close();
    }
    public static String toString(File file){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line+"\n");
            }
            String json = stringBuffer.toString();
            return json;
        }catch (Exception e){

        }
        return "";
    }
    public static String toString(String path) {
       return toString(new File(path));
    }

}