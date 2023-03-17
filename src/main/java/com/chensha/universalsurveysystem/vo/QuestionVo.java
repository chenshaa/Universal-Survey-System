package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class QuestionVo {

    /**
     * 问题id
     */
    private String id;

    /**
     * 问题内容
     */
    private String content;

    /**
     * 问题所属问卷id
     */
    private String questionnaireId;

    /**
     * 问题类型
     */
    private Integer questionType;

    /**
     * 问题选项
     */
    private String options;
}
