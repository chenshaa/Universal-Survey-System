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
     * 用户角色 0为普通用户，1为管理员
     */
    private Integer role;

    /**
     *token
     */
    private String token;
}
