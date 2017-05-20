package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.JobTask;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public interface JobTaskMapper {
    JobTask load(JobTask jobCode);
    List<JobTask> select(JobTask jobTask);
    List<JobTask> list();
    void insert(JobTask jobCode);
    void update(JobTask jobCode);
    void delete(JobTask jobCode);
}
