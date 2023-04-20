package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

@Data
public class PaperParams {
    /**
     * 用户id
     */
    private String respondentId;

    /**
     * 问卷id
     */
    private  String questionnaireId;
}
