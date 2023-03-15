package com.chensha.universalsurveysystem.service.impl;

import com.chensha.universalsurveysystem.dao.mapper.UserMapper;
import com.chensha.universalsurveysystem.dao.pojo.User;
import com.chensha.universalsurveysystem.service.UserService;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.utils.JWTUtils;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.UserLogin;
import com.chensha.universalsurveysystem.vo.papams.LoginParams;
import com.chensha.universalsurveysystem.service.sysUserService;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    private static final String slat = "chensha";

    private final UserMapper userMapper;
    private final sysUserService sysUserService;
    public UserServiceImpl(UserMapper userMapper, sysUserService sysUserService) {
        this.userMapper = userMapper;
        this.sysUserService = sysUserService;
    }

    /**
     *
     * @param loginParams 登录参数
     * @return jwt token
     */
    @Override
    public Result login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //判空
        if (account == null || password == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        password = DigestUtils.md5Hex(password + slat);
        User user = sysUserService.findUser(account, password);

        if (user == null) {
            return Result.fail(ErrorCode.ERROR_USERNAME.getCode(), ErrorCode.ERROR_USERNAME.getMsg());
        }
        String token = JWTUtils.createToken(user.getAccount());

        UserLogin userLogin = new UserLogin();
        userLogin.setAccount(user.getAccount());
        userLogin.setUsername(user.getUsername());
        userLogin.setToken(token);
        return Result.success(userLogin);
    }


}
