package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.dao.pojo.User;
import org.springframework.stereotype.Service;

public interface sysUserService {
    User findUser(String account, String password) ;

    boolean authToken(String authHeader) ;
}
