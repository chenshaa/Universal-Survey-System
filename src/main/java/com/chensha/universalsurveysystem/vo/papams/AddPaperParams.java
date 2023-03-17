package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

@Data
public class AddPaperParams {
    /**
     * 用户id
     */
    private String id;

    /**
     * 问卷id
     */
    private  String questionnaireId;
}
