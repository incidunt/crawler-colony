package com.dang.crawler.core.control.norm;

import com.dang.crawler.core.control.bean.Job;

/**
 * Created by dang on 17-5-11.
 */
public interface Notice<KEY,VALUE> {
    VALUE notice();
    Job put(KEY key, VALUE value);
    Job remove(KEY key);
}
