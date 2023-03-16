package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

@Data
public class LoginParams {
    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
}
