package com.dang.crawler.resources.compile.old;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mi on 2017/5/3.
 */
public class TestCompile{
   public static void main(String []args) throws Exception {
       String path = System.getProperty("user.dir") + "/crawler-core/src/main/java/com/dang/crawler/core/serivce/CreateTask.java";
       //Java源代码
       String sourceStr = fileToString(path);
       Class a = FlyCompile.compile(sourceStr);
      System.out.println(a);
   }
    public static String fileToString(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line = bufferedReader.readLine();
        if(!line.contains("package")){
            stringBuffer.append(line);
        }
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\t\n");
        }
        return stringBuffer.toString();
    }
}
