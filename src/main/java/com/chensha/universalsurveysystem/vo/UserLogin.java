package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class UserLogin {
    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String username;

    /**
     *
     */
    private String token;
}
