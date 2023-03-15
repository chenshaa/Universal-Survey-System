package com.chensha.universalsurveysystem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("answers")
public class Answer {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("question_id")
    private Integer questionId;

    @TableField("option_id")
    private Integer optionId;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;
}
