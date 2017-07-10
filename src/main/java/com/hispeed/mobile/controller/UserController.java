package com.hispeed.mobile.controller;

import com.hispeed.mobile.domain.User;
import com.hispeed.mobile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dengtianguang on 2017/7/10.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/insertUser")
    @ResponseBody
    public String insertUserInfo(){

        User user = new User();
        user.setUserId(123456);
        user.setUserAge(22);
        user.setUserName("SpringBoot");
        user.setUserAddress("USA");

        int result = userService.insertUserInfo(user);

        System.out.println("============"+result);

        return result+"";
    }

    @RequestMapping(value = "/gotoLoginOut")
    public String gotoLoginOut(HttpServletRequest request){

        return "timeOut";

    }
}
