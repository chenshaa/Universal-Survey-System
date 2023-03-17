package com.chensha.universalsurveysystem.service;

import com.chensha.universalsurveysystem.vo.Result;
import com.chensha.universalsurveysystem.vo.papams.AddQuestionParams;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface QuestionsService {
    Result addQuestion(String authHeader, AddQuestionParams addQuestionParams);

    Result listQuestion(String authHeader, String qid) ;

    Result deleteQuestion(String authHeader, String qid);
}
