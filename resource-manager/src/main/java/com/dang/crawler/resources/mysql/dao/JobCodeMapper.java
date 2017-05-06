package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.JobCode;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface JobCodeMapper {
    JobCode select(JobCode jobCode);
    List<JobCode> list();
    void insert(JobCode jobCode);
    void update(JobCode jobCode);
    void delete(JobCode jobCode);
}
