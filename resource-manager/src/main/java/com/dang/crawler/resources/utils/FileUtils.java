package com.dang.crawler.resources.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by duang on 2017/4/30.
 */
public class FileUtils {
    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void save(String path,byte[] bytes,boolean append) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            createFile(file);
        }
        OutputStream out = new FileOutputStream(file,append);
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
    public static boolean createFile(File file){
        if(file.exists()){
            return true;
        }else {
            File parent = file.getParentFile();
            if(!parent.exists()) {
                mkdir(parent);
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
    public static boolean mkdir(File file){
        if(file.exists()){
            return true;
        }else {
            File parent = file.getParentFile();
            if(!parent.exists()) {
                mkdir(parent);
            }
            file.mkdir();
            return true;
        }
    }

}