package com.dang.crawler.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    public static Logger log = LoggerFactory.getLogger(UserController.class);
    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
    public String login(String username, Model model, HttpSession httpSession){
        httpSession.setAttribute("loginUser",username);
        log.info("----------------------------------loginUser-------------------------------"+username);
        //return "redirect:../items/list.action";
        return "add";
    }
}
