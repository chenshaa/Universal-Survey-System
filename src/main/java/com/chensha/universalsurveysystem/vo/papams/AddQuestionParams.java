package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

@Data
public class AddQuestionParams {
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
     * 问题答案
     */
    private String answer;
}
