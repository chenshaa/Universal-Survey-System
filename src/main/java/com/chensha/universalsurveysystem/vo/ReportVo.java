package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class ReportVo {
    /**
     *
     */
    private String questionId;

    /**
     * 问题内容
     */
    private String questionContext;

    /**
     * 问题类型
     */
    private Integer questionType;

    /**
     * 问题选项
     */
    private String report;
}
