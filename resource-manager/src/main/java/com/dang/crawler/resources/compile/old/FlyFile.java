package com.dang.crawler.resources.compile.old;//FlyFile

//package com.jdjr.crawler.work.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangqihe on 2017/1/18.
 */
public class FlyFile {
  public List<String> onCreate(List args){
      List<String> list =new ArrayList<String>();
       System.out.println(" Fly onCreate");
       list.add("Fly onCreate");
       list.addAll(getList());
       list.add(""+System.currentTimeMillis());
      return list;
  }

    public List<String> getList() {
      List<String> strList =new ArrayList<String>();
      for(int i = 0;i<16;i++){
          strList.add("i="+i);
        }
        return strList;
    }
//        //动态编译
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        int flag = compiler.run(null, null, null,"E:/workspace/images/TestJava.java");
//        System.out.println(flag == 0 ? "编译成功" : "编译失败");

//        //两种动态执行编译方法
//        //1.通过Runtime.getRuntime();启动新的线程进行
//        Runtime run = Runtime.getRuntime();
//        Process process = run.exec("java -cp E:/workspace/images   TestJava");
//
//        InputStream is = process.getInputStream();
//        BufferedReader bis = new BufferedReader(new InputStreamReader(is));
//        String info = null;
//        while (null != (info = bis.readLine())) {
//            System.out.println(info);
//        }
//
//        //2.通过反射动态执行
//        try {
//            URL[] urls = new URL[] { new URL("file:/" + "E:/workspace/images/") };
//            URLClassLoader loader = new URLClassLoader(urls);
//            // 通过反射调用此类
//            Class clazz = loader.loadClass("TestJava");
//            Method m = clazz.getMethod("main", String[].class);
//            // m.invoke(null,new String[]{"aa","bb"});
//            // 由于可变参数是jdk5.0之后才有，上面代码会编译成m.invoke(null,"aa","bb");会发生参数不匹配的问题
//            // 因此必须加上Object 强转
//            m.invoke(null, (Object) new String[] {});
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}