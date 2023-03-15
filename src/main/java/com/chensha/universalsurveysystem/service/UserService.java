package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.LoginParams;

public interface UserService {
    Result login(LoginParams loginParams);
}
