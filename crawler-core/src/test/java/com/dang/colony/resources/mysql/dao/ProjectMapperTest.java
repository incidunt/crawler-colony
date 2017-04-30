package com.dang.colony.resources.mysql.dao;

import com.dang.colony.resources.common.ApplicationContextSingleton;
import com.dang.colony.resources.mysql.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class ProjectMapperTest {
    //@Resource
    private ProjectMapper projectMapper;
    @Before
    public void setUp() throws Exception {
        projectMapper= (ProjectMapper) ApplicationContextSingleton.getInstance().getBean("projectMapper");
    }
    @Test
    public void testQueryByTaskId() {
        Project project = projectMapper.selectById(1);
        System.out.println(project.getName());
    }
}
