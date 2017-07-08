package com.dang.crawler.core.control.norm;

/**
 * Created by dang on 17-5-10.
 * 缓存
 */
public interface Cache<KEY,VALUE> {
    VALUE get(KEY key);
    boolean put(KEY key,VALUE value);
    VALUE make(KEY key) throws Exception;
}
