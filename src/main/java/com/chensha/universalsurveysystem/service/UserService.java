package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddUserParams;
import com.chensha.universalsurveysystem.vo.papams.LoginParams;
import com.chensha.universalsurveysystem.vo.papams.UpdateUserParams;

public interface UserService {
    Result login(LoginParams loginParams);

    Result addUser(AddUserParams userParams, String authHeader);

    Result listUser(String authHeader);

    Result deleteUser(String authHeader, String userId);

    Result updateUser(String authHeader, UpdateUserParams updateUserParams);
}
