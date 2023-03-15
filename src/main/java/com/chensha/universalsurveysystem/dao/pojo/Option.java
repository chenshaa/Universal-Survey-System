package com.chensha.universalsurveysystem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("options")
public class Option {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField("question_id")
    private Integer questionId;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;
}
