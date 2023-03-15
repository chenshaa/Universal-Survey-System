package com.chensha.universalsurveysystem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {

    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 头像
     */
    @TableField("picture")
    private String picture;

    /**
     * 是否已删除用户
     */
    @TableField("delete")
    private Integer delete;

    /**
     * 是否已删除用户
     */
    @TableField("ban")
    private Integer ban;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Date updatedAt;
}