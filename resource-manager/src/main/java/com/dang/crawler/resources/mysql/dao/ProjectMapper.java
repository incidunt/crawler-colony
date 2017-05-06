package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.Project;

/**
 * Created by dangqihe on 2017/4/25.
 */
public interface ProjectMapper {
    Project selectById(int id);
}
