package com.chensha.universalsurveysystem.vo.papams;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class AddUserParams {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号
     */
    private String account;

    /**
     * 头像
     */
    private String picture;

    /**
     * 用户角色 0为普通用户，1为管理员
     */
    private Integer role;
}
