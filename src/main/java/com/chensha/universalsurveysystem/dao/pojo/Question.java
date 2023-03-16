package com.chensha.universalsurveysystem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("questions")
public class Question {

    /**
     * 问题id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 问题内容
     */
    @TableField("content")
    private String content;

    /**
     * 问题所属问卷id
     */
    @TableField("questionnaire_id")
    private String questionnaireId;

    /**
     * 问题类型
     */
    @TableField("question_type")
    private Integer questionType;

    /**
     * 问题答案
     */
    @TableField("answer")
    private String answer;

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
