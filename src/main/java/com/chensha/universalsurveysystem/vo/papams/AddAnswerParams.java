package com.chensha.universalsurveysystem.vo.papams;

import lombok.Data;

@Data
public class AddAnswerParams {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 问题id
     */
    private String questionId;


    /**
     * 问题答案
     */
    private String answer;
}
