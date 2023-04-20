package com.chensha.universalsurveysystem.vo;

import lombok.Data;

@Data
public class ReportOptionVo {
    /**
     * 选项id
     */
    private String optionId;

    /**
     * 选项内容
     */
    private String optionContext;

    /**
     * 选项数量
     */
    private Integer optionCount;
}
