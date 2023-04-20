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
     *
     * @param account  账号
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

    /**
     * 认证token是否有效
     *
     * @param authHeader 认证头
     * @return boolean
     */
    public boolean authToken(String authHeader) {
        String token = authHeader.substring(7);
        String account = JWTUtils.getAccount(token);
        return account != null;
    }

    /**
     * 验证token是否为管理员用户
     *
     * @param authHeader 认证头
     * @return boolean
     */
    @Override
    public boolean authToken4Admin(String authHeader) {
        String token = authHeader.substring(7);
        String account = JWTUtils.getAccount(token);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.eq(User::getRole, 1);
        queryWrapper.last("limit 1");

        User user = userMapper.selectOne(queryWrapper);
        return user != null;
    }

    /**
     * 判断用户是否存在
     *
     * @param account 账号
     * @return boolean
     */
    @Override
    public boolean isUserExist(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.last("limit 1");

        User user = userMapper.selectOne(queryWrapper);
        return user != null;
    }

    /**
     * 获取token中的账号
     *
     * @param authHeader 认证头
     * @return 账号
     */
    @Override
    public String getAccount(String authHeader) {
        String token = authHeader.substring(7);
        String account = JWTUtils.getAccount(token);
        return account;
    }

    /**
     * 通过账号获取用户id
     */
    @Override
    public String getUserIdByAccount(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.last("limit 1");

        return userMapper.selectOne(queryWrapper).getId();
    }

    /**
     * 通过用户id获取用户名
     *
     * @param id 用户id
     * @return 用户名
     */
    @Override
    public String getUserNameById(String id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        queryWrapper.last("limit 1");

        return userMapper.selectOne(queryWrapper).getUsername();
    }
}
