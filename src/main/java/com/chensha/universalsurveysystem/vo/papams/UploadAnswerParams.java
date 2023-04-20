package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

@Data
public class UploadAnswerParams {
    /**
     * 问卷id
     */
    private String questionnaireId;

    /**
     * 文本
     */
    private String optionContext;
}
