package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class QuestionnaireDetailVo {
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
     * 题目数组
     */
    private String questionList;
}
