package com.chensha.universalsurveysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chensha.universalsurveysystem.dao.mapper.UserMapper;
import com.chensha.universalsurveysystem.dao.pojo.User;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sysUserServiceImpl implements sysUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据账号密码查询用户
     * @param account 账号
     * @param password 密码
     * @return user
     */
    @Override
    public User findUser(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.eq(User::getPassword, password);
        queryWrapper.last("limit 1");

        return (userMapper.selectOne(queryWrapper));
    }

    public boolean authToken(String authHeader) {
        String token = authHeader.substring(7);
        String account = JWTUtils.getAccount(token);
        return account != null;
    }
}
