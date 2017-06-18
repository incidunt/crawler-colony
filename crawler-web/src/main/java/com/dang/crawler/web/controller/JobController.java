package com.dang.crawler.web.controller;

import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by dang on 17-6-16.
 */
@Controller
@RequestMapping("/job")
public class JobController {
    @Resource
    public CrawlerJobMapper crawlerJobMapper;
    public static Logger log = LoggerFactory.getLogger(UserController.class);
    @RequestMapping(value = "/list", method = {RequestMethod.POST,RequestMethod.GET})
    public String list(Model model, HttpSession httpSession) {
        List<CrawlerJob> crawlerJobList = crawlerJobMapper.list(new CrawlerJob());
        model.addAttribute(crawlerJobList);
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+crawlerJobList.size());
        return "list";
    }
}
