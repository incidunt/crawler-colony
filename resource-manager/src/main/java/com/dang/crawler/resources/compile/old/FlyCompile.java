package com.dang.crawler.resources.compile.old;


import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dangqihe on 2017/1/18.
 */
//http://www.weixueyuan.net/view/6104.html   onthe-fly
public class FlyCompile {
    private static String path = "D:\\Files\\MyCode\\class\\";//System.getProperty("user.dir") + "/crawler-core/src/main/java/com/dang/crawler/core/serivce/";

    public static Class compile(String  sourceStr) throws Exception {
        sourceStr = sourceStr.replaceAll("package","//package");
        int index = sourceStr.indexOf("public class");
        String clsName = sourceStr.substring(index,sourceStr.indexOf("",index));
        //当前编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //Java标准文件管理器
        StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);
        //Java文件对象
        JavaFileObject jfo = new StringJavaObject(clsName, sourceStr);
        //编译参数，类似于javac <options>中的options
        List<String> optionsList = new ArrayList<String>();
        //编译文件的存放地方，注意：此处是为Eclipse工具特设的
        optionsList.addAll(Arrays.asList("-d", path));
        //要编译的单元
        List<JavaFileObject> jfos = Arrays.asList(jfo);
        //设置编译环境
        JavaCompiler.CompilationTask task = compiler.getTask(null, fm, null, optionsList, null, jfos);
        //编译成功
        if (task.call()) {
            URL[] urls = new URL[]{new URL("file:/" + path)};
            URLClassLoader loader = new URLClassLoader(urls);
            // 通过反射调用此类
            Class clazz = loader.loadClass(clsName);
//            Method method = clazz.getMethod(methodName, Component.class,List.class, Product.class);//TODO 参数类型
            // m.invoke(null,new String[]{"aa","bb"});
            // 由于可变参数是jdk5.0之后才有，上面代码会编译成m.invoke(null,"aa","bb");会发生参数不匹配的问题
            // 因此必须加上Object 强转
            return clazz;
        } else {
            System.out.println("编译失败");
        }
        return null;
    }





    ////文本中的Java对象
    static class StringJavaObject extends SimpleJavaFileObject {
        //源代码
        private String content = "";

        //遵循Java规范的类名及文件
        public StringJavaObject(String _javaFileName, String _content) {
            super(_createStringJavaObjectUri(_javaFileName), Kind.SOURCE);
            content = _content;
        }

        //产生一个URL资源路径
        private static URI _createStringJavaObjectUri(String name) {
            //注意此处没有设置包名
            return URI.create("String:///" + name + Kind.SOURCE.extension);
        }

        //文本文件代码
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return content;
        }
    }
}
