package com.chensha.universalsurveysystem.controller;

import com.chensha.universalsurveysystem.service.UserService;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddUserParams;
import com.chensha.universalsurveysystem.vo.papams.LoginParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加用户
     * @param userParams 用户信息
     *
     */
    @PostMapping("addUser")
    public Result addUser(@RequestBody AddUserParams userParams, @RequestHeader("Authorization") String authHeader) {
        return userService.addUser(userParams,authHeader);
    }

    /**
     * 获取用户列表
     * @param authHeader 认证头
     * @return 用户列表
     */
    @GetMapping("listUser")
    public Result listUser(@RequestHeader("Authorization") String authHeader) {
        return userService.listUser(authHeader);
    }

    /**
     * 删除用户
     * @param authHeader 认证头
     * @param userId 用户id
     * @return 用户列表
     */
    @GetMapping("deleteUser")
    public Result deleteUser(@RequestHeader("Authorization") String authHeader,@RequestParam("userId") int userId) {
        return userService.deleteUser(authHeader,userId);
    }

    /**
     * 修改用户
     * @param authHeader 认证头
     * @param userParams 用户信息
     * @return 用户列表
     */
    @PostMapping("updateUser")
    public Result updateUser(@RequestHeader("Authorization") String authHeader,@RequestBody UpdateUserParams userParams) {
        return userService.updateUser(authHeader,userParams);
    }
}
