package com.chensha.universalsurveysystem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("questionnaires")
public class Questionnaire {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("user_id")
    private Integer userId;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;
}