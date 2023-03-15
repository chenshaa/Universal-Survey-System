package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.UserService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param loginParams 登录参数
     * @return 账号、昵称、jwt token
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginParams loginParams) {
        return userService.login(loginParams);
    }

}
