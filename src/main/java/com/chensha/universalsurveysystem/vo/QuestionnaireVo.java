package com.chensha.universalsurveysystem.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionnaireVo {
    /**
     * 问卷id
     */
    private String id;

    /**
     * 问卷标题
     */
    private String title;

    /**
     * 问卷描述
     */
    private String description;

    /**
     * 问卷创建者id
     */
    private String creatorId;

    /**
     * 问卷创建者id
     */
    private String creatorName;

    /**
     * 问卷是否匿名
     */
    private Boolean anonymousEnabled;

    /**
     *  问卷开始时间
     */
    private Date startTime;

    /**
     * 问卷结束时间
     */
    private Date endTime;
}
