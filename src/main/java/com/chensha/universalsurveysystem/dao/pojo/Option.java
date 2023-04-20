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

    /**
     * 选项id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 选项内容
     */
    @TableField("content")
    private String content;

    /**
     * 关联的问题id
     */
    @TableField("question_id")
    private String questionId;

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
