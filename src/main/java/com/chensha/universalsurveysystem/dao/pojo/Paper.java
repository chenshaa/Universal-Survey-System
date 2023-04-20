package com.chensha.universalsurveysystem.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("paper")
public class Paper {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 问卷ID
     */
    @TableField("questionnaire_id")
    private String questionnaireId;

    /**
     * 作答人ID
     */
    @TableField("respondent_id")
    private String respondentId;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;
}
