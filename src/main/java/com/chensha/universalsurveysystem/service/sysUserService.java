package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.dao.pojo.User;

public interface sysUserService {
    User findUser(String account, String password) ;

    boolean authToken(String authHeader) ;

    boolean authToken4Admin(String authHeader);

    boolean isUserExist(String account);

    String getAccount(String authHeader);

    String getUserIdByAccount(String account);

    String getUserNameById(String id);
}
