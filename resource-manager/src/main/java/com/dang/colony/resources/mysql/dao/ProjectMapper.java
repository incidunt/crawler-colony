package com.dang.colony.resources.mysql.dao;

import com.dang.colony.resources.mysql.model.Project;

/**
 * Created by dangqihe on 2017/4/25.
 */
public interface ProjectMapper {
    Project selectById(int id);
}
