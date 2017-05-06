package com.dang.crawler.resources.compile;

//测试类，从myclass文件中读出源码并在内存中编译
//http://www.cnblogs.com/eoss/p/6136943.html
public class DynaCompTest {
    public static void main(String[] args) throws Exception {
        String fullName = "com.seeyon.proxy.MyClass";
        String src = "package com.seeyon.proxy;\n" +
                "\n" +
                "public class MyClass {\n" +
                "\n" +
                "    public String say(String str){\n" +
                "        return \"hello\"+str;\n" +
                "    }\n" +
                "}";

        System.out.println(src);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance =  de.javaCodeToObject(fullName,src.toString());
        System.out.println(instance.getClass().getName());
    }
}