package com.chensha.universalsurveysystem.service.impl;

import com.chensha.universalsurveysystem.dao.mapper.UserMapper;
import com.chensha.universalsurveysystem.dao.pojo.User;
import com.chensha.universalsurveysystem.service.UserService;
import com.chensha.universalsurveysystem.service.sysUserService;
import com.chensha.universalsurveysystem.utils.JWTUtils;
import com.chensha.universalsurveysystem.vo.ErrorCode;
import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.UserLoginVo;
import com.chensha.universalsurveysystem.vo.papams.AddUserParams;
import com.chensha.universalsurveysystem.vo.papams.LoginParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateUserParams;
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

        UserLoginVo userLogin = new UserLoginVo();
        userLogin.setAccount(user.getAccount());
        userLogin.setUsername(user.getUsername());
        userLogin.setToken(token);
        return Result.success(userLogin);
    }

    @Override
    public Result addUser(AddUserParams addUserParams, String authHeader) {
        //判断账号是否为管理员
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        //判空
        if (addUserParams.getAccount() == null || addUserParams.getPassword() == null || addUserParams.getUsername() == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断账号是否已存在
        if(sysUserService.isUserExist(addUserParams.getAccount())){
            return Result.fail(ErrorCode.OBJECT_EXISTS.getCode(), ErrorCode.OBJECT_EXISTS.getMsg());
        }

        User user = new User();
        user.setAccount(addUserParams.getAccount());
        user.setUsername(addUserParams.getUsername());
        user.setPassword(DigestUtils.md5Hex(addUserParams.getPassword() + slat));
        user.setRole(addUserParams.getRole());
        userMapper.insert(user);
        return Result.success("成功");
    }

    @Override
    public Result listUser(String authHeader) {
        //判断账号是否为管理员
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        return Result.success(userMapper.selectList(null));
    }

    @Override
    public Result deleteUser(String authHeader, int userId) {
        //判断账号是否为管理员
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        userMapper.deleteById(userId);
        return Result.success("成功");
    }

    @Override
    public Result updateUser(String authHeader, UpdateUserParams userParams) {
        //判空
        if (userParams.getAccount() == null || userParams.getPassword() == null || userParams.getUsername() == null || userParams.getId() == null) {
            return Result.fail(ErrorCode.ERROR_PARAMETER.getCode(), ErrorCode.ERROR_PARAMETER.getMsg());
        }

        //判断账号是否为管理员
        if (!sysUserService.authToken4Admin(authHeader)) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }

        //更新用户信息
        User user = new User();
        user.setAccount(userParams.getAccount());
        user.setUsername(userParams.getUsername());
        user.setPassword(userParams.getPassword());
        user.setRole(userParams.getRole());
        user.setId(userParams.getId());
        userMapper.updateById(user);

        return Result.success("成功");
    }


}
