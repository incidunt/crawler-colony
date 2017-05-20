package com.dang.crawler.core.control.norm;

import com.dang.crawler.core.control.bean.Job;

/**
 * Created by dang on 17-5-15.
 */
public interface JobCounter extends Counter<Job,String,Integer>  {
    enum Name {
        thread("thread","thread"), crawler("crawler", "crawler"),taskToDo("taskToDo:", "taskToDo:"),taskFail("taskFail:", "taskFail:"),
        taskSuccess("taskSuccess:","taskSuccess:");
        // 成员变量
        private String name;
        private String value;
        // 构造方法
        private Name(String name, String value) {
            this.name = name;
            this.value = value;
        }
        public  String getName() {
            return name;
        }
        public  String getName(String suffix) {
            return name+suffix;
        }
        public String getValue() {
            return value;
        }
    }
    void flush();
}
