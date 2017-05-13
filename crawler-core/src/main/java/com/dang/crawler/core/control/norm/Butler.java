package com.dang.crawler.core.control.norm;

import java.util.Collection;

/**
 * Created by dang on 17-5-10.
 */
public interface Butler<KEY,VALUE>{
    boolean put(KEY key,VALUE value);
    int putAll(KEY key, Collection<? extends VALUE> values);
    VALUE get(KEY key);
    boolean remove(KEY key);
    int size(KEY key);
    int freeSize(KEY key);
}
