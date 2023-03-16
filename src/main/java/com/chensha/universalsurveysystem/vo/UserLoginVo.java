package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class UserLoginVo {
    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String username;

    /**
     *token
     */
    private String token;
}
