package com.dang.crawler.core.control.norm;


/**
 * Created by mi on 2017/5/12.
 */
public interface Counter<KEY,NAME,VALUE> {
    VALUE update(KEY key,NAME name,VALUE count);
    VALUE get(KEY key,NAME name);
    boolean remove(KEY key);
    //int subtract(String jobId,Attribute attribute,int subtract);
}
