package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionnaireTimeParams {
    /**
     * 问卷id
     */
    private String questionnaireId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

}
