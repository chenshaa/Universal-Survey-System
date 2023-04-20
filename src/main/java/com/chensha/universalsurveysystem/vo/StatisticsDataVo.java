package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class StatisticsDataVo {
    /**
     * 问题数量
     */
    private String questionNum;

    /**
     * 问卷数量
     */
    private String questionnaireNum;

    /**
     * 回答数量
     */
    private String answerNum;
}
