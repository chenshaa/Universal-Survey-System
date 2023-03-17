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

    /**
     * 问卷id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 问卷标题
     */
    @TableField("title")
    private String title;

    /**
     * 问卷描述
     */
    @TableField("description")
    private String description;

    /**
     * 问卷创建者id
     */
    @TableField("creator_id")
    private String creatorId;

    /**
     * 问卷是否匿名
     * 0为登录回答问卷，1为匿名回答问卷
     */
    @TableField("anonymous_enabled")
    private Boolean anonymousEnabled;

    /**
     *  问卷开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 问卷结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

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