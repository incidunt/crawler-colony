package com.dang.crawler.core.control.norm;


/**
 * Created by mi on 2017/5/12.
 */
public interface JobCounter {
        enum Attribute {
            thread("#thread","#thread"), crawler("#crawler", "#crawler");
            // 成员变量
            private String name;
            private String value;
            // 构造方法
            private Attribute(String name, String value) {
                this.name = name;
                this.value = value;
            }
            public  String getName() {
                return name;
            }
            public String getValue() {
                return value;
            }
    }
    int update(String jobId,String name,int count);
    //int subtract(String jobId,Attribute attribute,int subtract);
}
